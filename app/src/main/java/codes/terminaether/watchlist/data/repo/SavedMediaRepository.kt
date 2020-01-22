package codes.terminaether.watchlist.data.repo

import android.content.Context
import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.database.AppDatabase
import codes.terminaether.watchlist.data.model.ApiResult

/**
 * Handles saving media to the app's database, and contains methods for retrieving saved media.
 *
 * Created by terminaether on 2020-01-16.
 */
class SavedMediaRepository(private val context: Context) : BaseRepository() {

    private val detailsService = WatchlistApplication.INSTANCE.networkComponent.getDetailsService()

    /**
     * Fetch a Movie's full details and save to local database.
     */
    suspend fun insertMovie(movieId: Int) {
        val response = safeApiCall(
            call = { detailsService.getMovieDetails(movieId).await() },
            errorMessage = "Error Getting Details for Movie"
        )

        //TODO (UX): Use JobScheduler to try save Movie again
        when (response) {
            is ApiResult.Success -> {
                AppDatabase.DATABASE_WRITER.execute {
                    AppDatabase.getAppDatabase(context).movieDao().insertMovie(response.data)
                }
            }
        }
    }

    /**
     * Fetch a Show's full details and save to local database.
     */
    suspend fun insertShow(showId: Int) {
        val response = safeApiCall(
            call = { detailsService.getShowDetails(showId).await() },
            errorMessage = "Error Getting Details for Show"
        )

        //TODO (UX): Use JobScheduler to try save Show again
        when (response) {
            is ApiResult.Success -> {
                AppDatabase.DATABASE_WRITER.execute {
                    AppDatabase.getAppDatabase(context).showDao().insertShow(response.data)
                }
            }
        }
    }

    /**
     * Returns a combined list of all Movie and Show IDs stored in the database.
     */
    fun getMediaIds(): List<Int> {
        val movieIds = AppDatabase.getAppDatabase(context).movieDao().getAllMovieIds()
        val showIds = AppDatabase.getAppDatabase(context).showDao().getAllShowIds()

        val combinedList = movieIds.toMutableList()
        combinedList.addAll(showIds)

        return combinedList.toList()
    }

}