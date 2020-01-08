package codes.terminaether.watchlist.feature.discover.data.service

import codes.terminaether.watchlist.data.model.Movie
import codes.terminaether.watchlist.data.model.Show
import codes.terminaether.watchlist.feature.discover.data.model.DiscoverResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

/**
 * Contains functions for fetching Movies and TV Shows from TMDb's Discover endpoints.
 *
 * Created by terminaether on 2020-01-02.
 */
interface DiscoverService {

    @GET("discover/movie")
    fun discoverMovies(): Deferred<Response<DiscoverResponse<Movie>>>

    @GET("discover/tv")
    fun discoverShows(): Deferred<Response<DiscoverResponse<Show>>>

}