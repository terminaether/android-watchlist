package codes.terminaether.watchlist.data.model

import com.google.gson.annotations.SerializedName

/**
 * A data class describing a Show, as per TMDb.
 * @see [TV Details](https://developers.themoviedb.org/3/tv/get-tv-details)
 *
 * Created by terminaether on 2020-01-08.
 */
data class Show(
    /**
     * The path parameter to be supplied to `https://image.tmdb.org/t/p/{size}/{path}` in order to
     * get this Show's backdrop.
     */
    @SerializedName("backdrop_path")
    val backdropPath: String?,

    /**
     * Runtimes in minutes, may vary between season.
     */
    @SerializedName("episode_run_time")
    val episodeRuntime: IntArray?,

    /**
     * Formatted as YYYY-MM-DD.
     */
    @SerializedName("first_air_date")
    val firstAirDate: String?,

    /**
     * The Show's official website, if available.
     */
    val homepage: String?,

    /**
     * The ID for this Show within TMDb.
     */
    val id: Int?,

    @SerializedName("in_production")
    val inProduction: Boolean?,

    /**
     * ISO 639-1 codes.
     * @see [List of ISO 639-1 Codes](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)
     */
    val languages: Array<String>?,

    /**
     * Formatted as YYYY-MM-DD.
     */
    @SerializedName("last_air_date")
    val lastAirDate: String?,

    val name: String?,

    /**
     * Total number of episodes across all seasons.
     */
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,

    /**
     * ISO 3166-1 Alpha-2 codes.
     * @see [ISO 3166-1](https://en.wikipedia.org/wiki/ISO_3166-1)
     */
    @SerializedName("origin_country")
    val originCountry: Array<String>?,

    /**
     * ISO 639-1 code.
     * @see [List of ISO 639-1 Codes](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)
     */
    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_name")
    val originalName: String?,

    val overview: String?,

    /**
     * Any number from 0 to 'infinite' representing this Show's relative popularity.
     */
    val popularity: Number?,

    /**
     * The path parameter to be supplied to `https://image.tmdb.org/t/p/{size}/{path}` in order to
     * get this Show's poster.
     */
    @SerializedName("poster_path")
    val posterPath: String?,

    /**
     * One of: Planned, In Production, Pilot, Returning Series, Ended, Canceled.
     */
    val status: String?,

    /**
     * One of: Documentary, Miniseries, News, Reality, Scripted, Talk Show, Video.
     */
    val type: String?,

    /**
     * The average user rating, from 0.0 to 10.0.
     */
    @SerializedName("vote_average")
    val voteAverage: Number?,

    /**
     * The number of individual votes/ratings cast on this Show.
     */
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Show

        if (episodeRuntime != null) {
            if (other.episodeRuntime == null) return false
            if (!episodeRuntime.contentEquals(other.episodeRuntime)) return false
        } else if (other.episodeRuntime != null) return false
        if (languages != null) {
            if (other.languages == null) return false
            if (!languages.contentEquals(other.languages)) return false
        } else if (other.languages != null) return false
        if (originCountry != null) {
            if (other.originCountry == null) return false
            if (!originCountry.contentEquals(other.originCountry)) return false
        } else if (other.originCountry != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = episodeRuntime?.contentHashCode() ?: 0
        result = 31 * result + (languages?.contentHashCode() ?: 0)
        result = 31 * result + (originCountry?.contentHashCode() ?: 0)
        return result
    }
}