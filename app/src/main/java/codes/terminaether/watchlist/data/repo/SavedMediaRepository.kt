package codes.terminaether.watchlist.data.repo

import android.content.Context
import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.database.AppDatabase
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.model.Media

/**
 * Handles saving media to the app's database, and contains methods for retrieving saved media.
 *
 * Created by terminaether on 2020-01-16.
 */
//TODO (Refactor): Convert to singular MediaRepository
class SavedMediaRepository(private val context: Context) : BaseRepository() {

    private val detailsService = WatchlistApplication.INSTANCE.networkComponent.getDetailsService()

    //TODO (Refactor): Insert with flag; SAVED, SEARCH, DISCOVER
    suspend fun insertMedia(media: Media) {
        if (media.isMovie) {
            insertMovie(media.id)
        } else {
            insertShow(media.id)
        }
    }

    fun deleteMedia(media: Media) {
        AppDatabase.getAppDatabase(context).mediaDao().deleteMediaById(media.id)
    }

    /**
     * Returns a combined list of all Movie and Show IDs stored in the database.
     */
    //TODO (Refactor): getSavedMediaIds (Method may not be necessary)
    fun getMediaIds(): List<Int> {
        return AppDatabase.getAppDatabase(context).mediaDao().getSavedMediaIds()
    }

    /**
     * Fetch a Movie's full details and save to local database.
     */
    private suspend fun insertMovie(movieId: Int) {
        val response = safeApiCall(
            call = { detailsService.getMovieDetails(movieId).await() },
            errorMessage = "Error Getting Details for Movie"
        )

        //TODO (UX): Use JobScheduler to try save Movie again
        when (response) {
            is ApiResult.Success -> {
                AppDatabase.DATABASE_WRITER.execute {
                    AppDatabase.getAppDatabase(context).mediaDao().saveMedia(response.data)
                }
            }
        }
    }

    /**
     * Fetch a Show's full details and save to local database.
     */
    private suspend fun insertShow(showId: Int) {
        val response = safeApiCall(
            call = { detailsService.getShowDetails(showId).await() },
            errorMessage = "Error Getting Details for Show"
        )

        //TODO (UX): Use JobScheduler to try save Show again
        when (response) {
            is ApiResult.Success -> {
                AppDatabase.DATABASE_WRITER.execute {
                    AppDatabase.getAppDatabase(context).mediaDao().saveMedia(response.data)
                }
            }
        }
    }

}