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

    suspend fun insertMovie(movieId: Int) {
        val response = safeApiCall(
            call = { detailsService.getMovieDetails(movieId).await() },
            errorMessage = "Error Getting Details for Movie"
        )

        when (response) {
            is ApiResult.Success -> {
                AppDatabase.DATABASE_WRITER.execute {
                    AppDatabase.getAppDatabase(context).movieDao().insertMovie(response.data)
                }
            }
        }
    }

    suspend fun insertShow(showId: Int) {
        val response = safeApiCall(
            call = { detailsService.getShowDetails(showId).await() },
            errorMessage = "Error Getting Details for Show"
        )

        when (response) {
            is ApiResult.Success -> {
                AppDatabase.DATABASE_WRITER.execute {
                    AppDatabase.getAppDatabase(context).showDao().insertShow(response.data)
                }
            }
        }
    }

}