package codes.terminaether.watchlist.feature.discover.data.repo

import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.repo.BaseRepository
import codes.terminaether.watchlist.feature.discover.data.model.DiscoverMoviesResponse

/**
 * Allows the app to use multiple data sources for the Discover feed.
 *
 * Created by terminaether on 2020-01-03.
 */
class DiscoverRepository : BaseRepository() {

    private val discoverService =
        WatchlistApplication.INSTANCE.networkComponent.getDiscoverService()

    suspend fun discoverMovies(): ApiResult<DiscoverMoviesResponse> {
        return safeApiCall(
            call = { discoverService.discoverMovies().await() },
            errorMessage = "Error Discovering Movies"
        )
    }

}