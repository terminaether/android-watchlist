package codes.terminaether.watchlist.data.repo

import android.content.Context
import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.database.AppDatabase
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.feature.discover.data.model.DiscoverResponse

/**
 * Handles all database and networking operations for the app, and acts as a single source of truth
 * for Media. Contains methods for:
 * - Fetching popular media from TMDb
 * - Saving media to the app's local database
 * - Retrieving saved media from the app's local database
 *
 * Created by terminaether on 2020-01-16.
 */
class MediaRepository(private val context: Context) : BaseRepository() {

    private val mediaDao = AppDatabase.getAppDatabase(context).mediaDao()
    private val detailsService = WatchlistApplication.INSTANCE.networkComponent.getDetailsService()
    private val discoverService =
        WatchlistApplication.INSTANCE.networkComponent.getDiscoverService()

    val discoverMovieList = mediaDao.discoverMovieResults
    val discoverShowList = mediaDao.discoverShowResults

    /**
     * Updates the local database by making a call to TMDb's Discover endpoint.
     */
    suspend fun refreshDiscoverResults(discoverMovies: Boolean) {
        //TODO (Database): Only update if the contents of the database are older than X
        //TODO (Localisation): Localise discoverService error messages
        val discoverResponse: ApiResult<DiscoverResponse<Media>> = if (discoverMovies) {
            safeApiCall(
                call = { discoverService.discoverMovies().await() },
                errorMessage = "Error Discovering Movies"
            )
        } else {
            safeApiCall(
                call = { discoverService.discoverShows().await() },
                errorMessage = "Error Discovering Shows"
            )
        }

        when (discoverResponse) {
            is ApiResult.Success -> {
                val cleanDataSet = removeInvalidData(discoverResponse.data.results)
                mediaDao.upsertAll(cleanDataSet)
            }
            is ApiResult.Error -> {
                throw discoverResponse.exception
            }
        }
    }

    /**
     * Fetch a Media object's full details and update the local database.
     */
    suspend fun saveMedia(media: Media) {
        //TODO (Localisation): Localise detailsService error messages
        val detailsResponse: ApiResult<Media> = if (media.isMovie) {
            safeApiCall(
                call = { detailsService.getMovieDetails(media.id).await() },
                errorMessage = "Error Getting Details for Movie"
            )
        } else {
            safeApiCall(
                call = { detailsService.getShowDetails(media.id).await() },
                errorMessage = "Error Getting Details for Show"
            )
        }

        //TODO (UX): Use JobScheduler to try save Show again
        when (detailsResponse) {
            is ApiResult.Success -> {
                val fullMedia = detailsResponse.data
                fullMedia.isSaved = true
                AppDatabase.getAppDatabase(context).mediaDao().update(fullMedia)
            }
            is ApiResult.Error -> {
                throw detailsResponse.exception
            }
        }
    }

    //TODO (Database): Items are updated, but list is not refreshed
    suspend fun unsaveMedia(media: Media) {
        media.isSaved = false
        AppDatabase.getAppDatabase(context).mediaDao().update(media)
    }

    /**
     * Removes Media items without a valid ID, Poster Path and Title/Name.
     * With thanks to Conor.
     */
    private fun removeInvalidData(dataSet: List<Media>): List<Media> {
        val cleanDataSet = dataSet.toMutableList()
        cleanDataSet.removeAll { it.id == 0 }
        cleanDataSet.removeAll { it.posterPath.isNullOrBlank() }

        val iterator = cleanDataSet.iterator()
        iterator.forEach {
            if (it.title.isNullOrBlank() && it.name.isNullOrBlank()) iterator.remove()
        }

        return cleanDataSet
    }

}