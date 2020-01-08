package codes.terminaether.watchlist.feature.discover.data.repo

import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.model.Movie
import codes.terminaether.watchlist.data.model.Show
import codes.terminaether.watchlist.data.repo.BaseRepository
import codes.terminaether.watchlist.feature.discover.data.model.DiscoverResponse

/**
 * Allows the app to use multiple data sources for the Discover feed.
 *
 * Created by terminaether on 2020-01-03.
 */
class DiscoverRepository : BaseRepository() {

    private val discoverService =
        WatchlistApplication.INSTANCE.networkComponent.getDiscoverService()

    suspend fun discoverMovies(): ApiResult<DiscoverResponse<Movie>> {
        return safeApiCall(
            call = { discoverService.discoverMovies().await() },
            errorMessage = "Error Discovering Movies"
        )
    }

    suspend fun discoverShows(): ApiResult<DiscoverResponse<Show>> {
        return safeApiCall(
            call = { discoverService.discoverShows().await() },
            errorMessage = "Error Discovering Shows"
        )
    }

}