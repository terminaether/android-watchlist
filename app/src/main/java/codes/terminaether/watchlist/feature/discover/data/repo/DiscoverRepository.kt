package codes.terminaether.watchlist.feature.discover.data.repo

import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.model.*
import codes.terminaether.watchlist.data.repo.BaseRepository

/**
 * Allows the app to use multiple data sources for the Discover feed.
 *
 * Created by terminaether on 2020-01-03.
 */
class DiscoverRepository : BaseRepository() {

    private val discoverService =
        WatchlistApplication.INSTANCE.networkComponent.getDiscoverService()

    suspend fun discoverMovies(): UiState<List<Media>> {
        val response = safeApiCall(
            call = { discoverService.discoverMovies().await() },
            errorMessage = "Error Discovering Movies"
        )

        return when (response) {
            is ApiResult.Success -> {
                val cleanDataSet = removeInvalidData(response.data.results)
                UiState.Success(cleanDataSet)
            }
            is ApiResult.Error -> {
                UiState.Error(response.exception)
            }
        }
    }

    suspend fun discoverShows(): UiState<List<Media>> {
        val response = safeApiCall(
            call = { discoverService.discoverShows().await() },
            errorMessage = "Error Discovering Shows"
        )

        return when (response) {
            is ApiResult.Success -> {
                val cleanDataSet = removeInvalidData(response.data.results)
                UiState.Success(cleanDataSet)
            }
            is ApiResult.Error -> {
                UiState.Error(response.exception)
            }
        }
    }

    private fun removeInvalidData(dataSet: List<Media>): List<Media> {
        val cleanDataSet = dataSet.toMutableList()
        cleanDataSet.removeAll { it.posterPath.isNullOrBlank() }

        val iterator = cleanDataSet.iterator()
        iterator.forEach {
            if (it is Movie) {
                if (it.title.isNullOrBlank()) iterator.remove()
            } else if (it is Show) {
                if (it.name.isNullOrBlank()) iterator.remove()
            }
        }

        return cleanDataSet
    }

}