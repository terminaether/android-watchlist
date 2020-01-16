package codes.terminaether.watchlist.data.model

import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

/**
 * A class describing a Show, as per TMDb.
 * @see [TV Details](https://developers.themoviedb.org/3/tv/get-tv-details)
 *
 * Created by terminaether on 2020-01-08.
 */
@Entity
class Show(
    /**
     * Runtimes in minutes, may vary between season.
     */
    @SerializedName("episode_run_time")
    @Ignore
    var episodeRuntime: IntArray? = null,

    /**
     * Formatted as YYYY-MM-DD.
     */
    @SerializedName("first_air_date")
    var firstAirDate: String? = null,

    @SerializedName("in_production")
    var inProduction: Boolean? = null,

    /**
     * ISO 639-1 codes.
     * @see [List of ISO 639-1 Codes](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)
     */
    @Ignore
    var languages: Array<String>? = null,

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
    var numberOfEpisodes: Int? = null,

    @SerializedName("number_of_seasons")
    var numberOfSeasons: Int? = null,

    /**
     * ISO 3166-1 Alpha-2 codes.
     * @see [ISO 3166-1](https://en.wikipedia.org/wiki/ISO_3166-1)
     */
    @SerializedName("origin_country")
    @Ignore
    var originCountry: Array<String>? = null,

    @SerializedName("original_name")
    var originalName: String? = null,

    /**
     * One of: Documentary, Miniseries, News, Reality, Scripted, Talk Show, Video.
     */
    var type: String? = null
) : Media()