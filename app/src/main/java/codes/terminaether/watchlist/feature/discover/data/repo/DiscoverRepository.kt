package codes.terminaether.watchlist.feature.discover.data.repo

import android.content.Context
import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.database.AppDatabase
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.model.UiState
import codes.terminaether.watchlist.data.repo.BaseRepository
import codes.terminaether.watchlist.feature.discover.data.model.DiscoverResponse

/**
 * Allows the app to use multiple data sources for the Discover feed.
 *
 * Created by terminaether on 2020-01-03.
 */
class DiscoverRepository(context: Context) : BaseRepository() {

    private val discoverDao = AppDatabase.getAppDatabase(context).discoverDao()
    private val discoverService =
        WatchlistApplication.INSTANCE.networkComponent.getDiscoverService()

    //TODO (Code): Media returned should be observable?
    suspend fun discoverMedia(
        discoverMovies: Boolean,
        forceUpdate: Boolean
    ): UiState<List<Media>> {
        val discoverResults: List<Media> = if (discoverMovies) {
            discoverDao.getDiscoverMovieResults()
        } else {
            discoverDao.getDiscoverShowResults()
        }

        if (discoverResults.isNotEmpty() && !forceUpdate) {
            return UiState.Success(discoverResults)
        }

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

        return when (response) {
            is ApiResult.Success -> {
                val cleanDataSet = removeInvalidData(response.data.results)
                discoverDao.insertDiscoverResults(cleanDataSet)
                UiState.Success(cleanDataSet)
            }
            is ApiResult.Error -> {
                UiState.Error(response.exception)
            }
        }
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