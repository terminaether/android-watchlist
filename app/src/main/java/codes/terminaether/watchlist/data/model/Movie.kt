package codes.terminaether.watchlist.data.model

import com.google.gson.annotations.SerializedName

/**
 * A data class describing a Movie, as per The Movie Database.
 *
 * Created by terminaether on 2019-12-24.
 */
data class Movie(
    /**
     * True if this is a pornographic Movie.
     */
    val adult: Boolean?,

    /**
     * The path parameter to be supplied to `https://image.tmdb.org/t/p/{size}/{path}' in order to
     * get this Movie's backdrop.
     */
    @SerializedName("backdrop_path")
    val backdropPath: String?,

    /**
     * Budget in USD.
     */
    val budget: Int?,

    /**
     * The Movie's official website, if available.
     */
    val homepage: String?,

    /**
     * The ID for this Movie within TMDb.
     */
    val id: Int?,

    /**
     * 9 character ID with pattern ^tt[0-9]{7}.
     */
    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    val overview: String?,

    /**
     * Any number from 0 to 'infinite' representing this Movie's relative popularity.
     */
    val popularity: Number?,

    /**
     * The path parameter to be supplied to `https://image.tmdb.org/t/p/{size}/{path}' in order to
     * get this Movie's poster.
     */
    @SerializedName("poster_path")
    val posterPath: String?,

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

    /**
     * One of: Rumored, Planned, In Production, Post Production, Released, Canceled.
     */
    val status: String?,

    val tagline: String?,

    val title: String?,

    /**
     * True if there are available trailers, bloopers or behind the scenes videos for this Movie.
     * To retrieve videos for this Movie, a call must be made to `/movie/{movie_id}/videos`.
     */
    val video: Boolean?,

    /**
     * The average user rating, from 0.0 to 10.0.
     */
    @SerializedName("vote_average")
    val voteAverage: Number?,

    /**
     * The number of individual votes/ratings cast on this Movie.
     */
    @SerializedName("vote_count")
    val voteCount: Int?
)