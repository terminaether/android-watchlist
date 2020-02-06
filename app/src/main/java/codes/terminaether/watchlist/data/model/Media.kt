package codes.terminaether.watchlist.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * A class describing a Movie or Show object.
 *
 * Created by terminaether on 2020-01-10.
 */
@Entity
data class Media(
    //region Non-API Variables
    var isFavourite: Boolean = false,
    //endregion

    //region Shared API Variables
    /**
     * The path parameter to be supplied to `https://image.tmdb.org/t/p/{size}/{path}` in order to
     * get this Media's backdrop.
     */
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @Ignore
    var genres: List<Genre>? = listOf(),

    /**
     * The Media's official website, if available.
     */
    var homepage: String? = null,

    /**
     * The ID for this Media within TMDb.
     */
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    /**
     * ISO 639-1 code.
     * @see [List of ISO 639-1 Codes](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)
     */
    @SerializedName("original_language")
    var originalLanguage: String? = null,

    var overview: String? = null,

    /**
     * Any number from 0 to 'infinite' representing this Show's relative popularity.
     */
    var popularity: Double? = 0.0,

    /**
     * The path parameter to be supplied to `https://image.tmdb.org/t/p/{size}/{path}` in order to
     * get this Show's poster.
     */
    @SerializedName("poster_path")
    var posterPath: String? = null,

    /**
     * For a Movie, one of: Rumored, Planned, In Production, Post Production, Released, Canceled.
     * For a Show, one of: Planned, In Production, Pilot, Returning Series, Ended, Canceled.
     */
    var status: String? = null,

    /**
     * The average user rating, from 0.0 to 10.0.
     */
    @SerializedName("vote_average")
    var voteAverage: Double? = 0.0,

    /**
     * The number of individual votes/ratings cast on this Media.
     */
    @SerializedName("vote_count")
    var voteCount: Int? = 0,
    //endregion

    //region Movie API Variables
    /**
     * True if this is a pornographic Movie.
     */
    var adult: Boolean? = false,

    /**
     * Budget in USD.
     */
    var budget: Int? = 0,

    /**
     * 9 character ID with pattern ^tt[0-9]{7}.
     */
    @SerializedName("imdb_id")
    var imdbId: String? = null,

    @SerializedName("original_title")
    var originalTitle: String? = null,

    /**
     * Release date, formatted as YYYY-MM-DD.
     */
    @SerializedName("release_date")
    var releaseDate: String? = null,

    /**
     * Worldwide box office revenue in USD.
     */
    var revenue: Int? = 0,

    /**
     * Runtime in minutes.
     */
    var runtime: Int? = 0,

    var tagline: String? = null,

    var title: String? = null,

    /**
     * True if there are available trailers, bloopers or behind the scenes videos for this Movie.
     * To retrieve videos for this Movie, a call must be made to `/movie/{movie_id}/videos`.
     */
    var video: Boolean? = false,
    //endregion

    //region Show API Variables
    /**
     * Runtimes in minutes, may vary between season.
     */
    @SerializedName("episode_run_time")
    @Ignore
    var episodeRuntime: List<Int>? = listOf(),

    /**
     * Formatted as YYYY-MM-DD.
     */
    @SerializedName("first_air_date")
    var firstAirDate: String? = null,

    @SerializedName("in_production")
    var inProduction: Boolean? = false,

    /**
     * ISO 639-1 codes.
     * @see [List of ISO 639-1 Codes](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)
     */
    @Ignore
    var languages: List<String>? = listOf(),

    /**
     * Formatted as YYYY-MM-DD.
     */
    @SerializedName("last_air_date")
    var lastAirDate: String? = null,

    var name: String? = null,

    /**
     * Total number of episodes across all seasons.
     */
    @SerializedName("number_of_episodes")
    var numberOfEpisodes: Int? = 0,

    @SerializedName("number_of_seasons")
    var numberOfSeasons: Int? = 0,

    /**
     * ISO 3166-1 Alpha-2 codes.
     * @see [ISO 3166-1](https://en.wikipedia.org/wiki/ISO_3166-1)
     */
    @SerializedName("origin_country")
    @Ignore
    var originCountry: List<String>? = listOf(),

    @SerializedName("original_name")
    var originalName: String? = null,

    /**
     * One of: Documentary, Miniseries, News, Reality, Scripted, Talk Show, Video.
     */
    var type: String? = null
    //endregion
) {
    val isMovie: Boolean
        get() = !title.isNullOrBlank()

    val mediaTitle: String?
        get() {
            return if (isMovie) {
                title
            } else {
                name
            }
        }

    val originalMediaTitle: String?
        get() {
            return if (isMovie) {
                originalTitle
            } else {
                originalName
            }
        }

    /**
     * Returns a string which includes all the drawable elements of a Media item.
     * Used for equality operators in RecyclerView adapters.
     */
    fun describeDrawableContents(): String {
        return "$posterPath, $mediaTitle, ${getConciseOverview()}, $isFavourite"
    }

    /**
     * Shortens the Media overview for use in Discover and Favourites feeds.
     */
    fun getConciseOverview(): String {
        var overviewSubString = overview?.substringBefore(".")
        if (!overviewSubString.isNullOrBlank()) {
            if (overviewSubString.length <= 60) {
                overviewSubString += "."
                //If the overview is too short, extend to the end of the next sentence
                val extendedSubString = overview!!.substringAfter(".")
                overviewSubString += extendedSubString.substringBefore(".")
            }
        } else {
            return ""
        }
        overviewSubString += "."

        return overviewSubString
    }

}