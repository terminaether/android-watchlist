package codes.terminaether.watchlist.data.model

import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * A class describing the shared properties of [Movie] and [Show] models.
 *
 * Created by terminaether on 2020-01-10.
 */
open class Media(
    /**
     * The path parameter to be supplied to `https://image.tmdb.org/t/p/{size}/{path}` in order to
     * get this Media's backdrop.
     */
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @Ignore
    var genres: List<Genre>? = null,

    /**
     * The Media's official website, if available.
     */
    var homepage: String? = null,

    /**
     * The ID for this Media within TMDb.
     */
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,

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
    @Ignore
    var popularity: Number? = null,

    /**
     * The path parameter to be supplied to `https://image.tmdb.org/t/p/{size}/{path}` in order to
     * get this Show's poster.
     */
    @SerializedName("poster_path")
    var posterPath: String? = null,

    /**
     * For a [Movie], one of: Rumored, Planned, In Production, Post Production, Released, Canceled.
     * For a [Show], one of: Planned, In Production, Pilot, Returning Series, Ended, Canceled.
     */
    var status: String? = null,

    /**
     * The average user rating, from 0.0 to 10.0.
     */
    @SerializedName("vote_average")
    @Ignore
    var voteAverage: Number? = null,

    /**
     * The number of individual votes/ratings cast on this Media.
     */
    @SerializedName("vote_count")
    var voteCount: Int? = null
)