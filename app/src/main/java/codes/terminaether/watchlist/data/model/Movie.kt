package codes.terminaether.watchlist.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * A class describing a Movie, as per TMDb.
 * @see [Movie Details](https://developers.themoviedb.org/3/movies/get-movie-details).
 *
 * Created by terminaether on 2019-12-24.
 */
@Entity
data class Movie(
    /**
     * True if this is a pornographic Movie.
     */
    val adult: Boolean?,

    /**
     * Budget in USD.
     */
    val budget: Int?,

    /**
     * 9 character ID with pattern ^tt[0-9]{7}.
     */
    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    /**
     * Release date, formatted as YYYY-MM-DD.
     */
    @SerializedName("release_date")
    val releaseDate: String?,

    /**
     * Worldwide box office revenue in USD.
     */
    val revenue: Int?,

    /**
     * Runtime in minutes.
     */
    val runtime: Int?,

    val tagline: String?,

    val title: String?,

    /**
     * True if there are available trailers, bloopers or behind the scenes videos for this Movie.
     * To retrieve videos for this Movie, a call must be made to `/movie/{movie_id}/videos`.
     */
    val video: Boolean?
) : Media()