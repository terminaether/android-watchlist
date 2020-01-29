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
class SavedMediaRepository(private val context: Context) : BaseRepository() {

    private val detailsService = WatchlistApplication.INSTANCE.networkComponent.getDetailsService()

    /**
     * Returns a combined list of all Movie and Show IDs stored in the database.
     */
    fun getSavedMediaIds(): List<Int> {
        return AppDatabase.getAppDatabase(context).mediaDao().getSavedMediaIds()
    }

    /**
     * Fetch a Media object's full details and update the local database.
     */
    suspend fun saveMedia(media: Media) {
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
                AppDatabase.DATABASE_WRITER.execute {
                    val fullMedia = detailsResponse.data
                    fullMedia.isSaved = true
                    AppDatabase.getAppDatabase(context).mediaDao().updateMedia(fullMedia)
                }
            }
        }
    }

    fun unsaveMedia(media: Media) {
        media.isSaved = false
        AppDatabase.getAppDatabase(context).mediaDao().updateMedia(media)
    }

    fun deleteMedia(media: Media) {
        AppDatabase.getAppDatabase(context).mediaDao().deleteMediaById(media.id)
    }

}