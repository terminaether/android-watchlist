package codes.terminaether.watchlist.feature.discover.data.repo

import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.model.Movie
import codes.terminaether.watchlist.data.model.Show
import codes.terminaether.watchlist.data.model.UiState
import codes.terminaether.watchlist.data.repo.BaseRepository

/**
 * Allows the app to use multiple data sources for the Discover feed.
 *
 * Created by terminaether on 2020-01-03.
 */
class DiscoverRepository : BaseRepository() {

    private val discoverService =
        WatchlistApplication.INSTANCE.networkComponent.getDiscoverService()

    suspend fun discoverMovies(): UiState<List<Movie>> {
        val response = safeApiCall(
            call = { discoverService.discoverMovies().await() },
            errorMessage = "Error Discovering Movies"
        )

        return when (response) {
            is ApiResult.Success -> {
                UiState.Success(response.data.results)
            }
            is ApiResult.Error -> {
                UiState.Error(response.exception)
            }
        }
    }

    suspend fun discoverShows(): UiState<List<Show>> {
        val response = safeApiCall(
            call = { discoverService.discoverShows().await() },
            errorMessage = "Error Discovering Shows"
        )

        return when (response) {
            is ApiResult.Success -> {
                UiState.Success(response.data.results)
            }
            is ApiResult.Error -> {
                UiState.Error(response.exception)
            }
        }
    }

}