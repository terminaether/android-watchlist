package codes.terminaether.watchlist.feature.discover.data.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.database.AppDatabase
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.repo.BaseRepository
import codes.terminaether.watchlist.feature.discover.data.model.DiscoverResponse

/**
 * Allows the app to use multiple data sources for the Discover feed.
 *
 * Created by terminaether on 2020-01-03.
 */
class DiscoverRepository(context: Context) : BaseRepository() {

    private val mediaDao = AppDatabase.getAppDatabase(context).mediaDao()
    private val discoverService =
        WatchlistApplication.INSTANCE.networkComponent.getDiscoverService()

    //TODO (Code): Media returned should be observable?
    suspend fun discoverMedia(
        discoverMovies: Boolean,
        forceUpdate: Boolean
    ): List<Media> {
        var discoverResults: List<Media> = if (discoverMovies) {
            mediaDao.getDiscoverMovieResults()
        } else {
            mediaDao.getDiscoverShowResults()
        }

        if (discoverResults.isNullOrEmpty() || forceUpdate) {
            val response: ApiResult<DiscoverResponse<Media>> = if (discoverMovies) {
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

            when (response) {
                is ApiResult.Success -> {
                    Log.d("Attention", "API Success")
                    val cleanDataSet = removeInvalidData(response.data.results)
                    mediaDao.insertDiscoverResults(cleanDataSet)
                    discoverResults = if (discoverMovies) {
                        mediaDao.getDiscoverMovieResults()
                    } else {
                        mediaDao.getDiscoverShowResults()
                    }
                }
            }
        }

        return discoverResults
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