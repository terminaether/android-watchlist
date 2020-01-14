package codes.terminaether.watchlist.data.repo

import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.model.Genre

/**
 * Provides Genre fetch methods to multiple locations throughout the app.
 *
 * Created by terminaether on 2020-01-14.
 */
class GenreRepository : BaseRepository() {

    private val genreService = WatchlistApplication.INSTANCE.networkComponent.getGenreService()

    fun getGenreNameById(id: Int): String {
        //TODO (Code): Search local DB for this ID and return the Genre's name
        //TODO (Code): If the DB is empty, or old, fetch Genres from server
        return "Genre"
    }

    private suspend fun fetchMovieGenres(): List<Genre>? {
        val movieGenresResponse = safeApiCall(
            call = { genreService.getMovieGenres().await() },
            errorMessage = "Error Fetching Movie Genres"
        )

        return when (movieGenresResponse) {
            is ApiResult.Success -> {
                movieGenresResponse.data
            }
            is ApiResult.Error -> {
                null
            }
        }
    }

    private suspend fun fetchShowGenres(): List<Genre>? {
        val movieGenresResponse = safeApiCall(
            call = { genreService.getShowGenres().await() },
            errorMessage = "Error Fetching Show Genres"
        )

        return when (movieGenresResponse) {
            is ApiResult.Success -> {
                movieGenresResponse.data
            }
            is ApiResult.Error -> {
                null
            }
        }
    }

}