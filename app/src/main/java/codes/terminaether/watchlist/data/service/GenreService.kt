package codes.terminaether.watchlist.data.service

import codes.terminaether.watchlist.data.model.Genre
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

/**
 * Contains functions for fetching Genres from TMDBs two Genre endpoints.
 *
 * Created by terminaether on 2020-01-14.
 */
interface GenreService {

    @GET("genre/movie/list")
    fun getMovieGenres(): Deferred<Response<Genre>>

    @GET("genre/tv/list")
    fun getShowGenres(): Deferred<Response<Genre>>

}