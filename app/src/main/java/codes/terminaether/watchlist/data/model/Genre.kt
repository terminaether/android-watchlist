package codes.terminaether.watchlist.data.model

/**
 * A simple data class representing a Genre for a [Movie] or [Show].
 * @see [Genres](https://developers.themoviedb.org/3/genres).
 *
 * Created by terminaether on 2020-01-14.
 */
data class Genre(val id: Int, val name: String)