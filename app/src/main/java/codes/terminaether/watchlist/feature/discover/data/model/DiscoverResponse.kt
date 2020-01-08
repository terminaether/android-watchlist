package codes.terminaether.watchlist.feature.discover.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class to describe the response given by TMDb's `/discover/movie` endpoint.
 *
 * Created by terminaether on 2020-01-02.
 */
data class DiscoverResponse<T>(
    /**
     * This response's page number.
     */
    val page: Int,

    /**
     * Note: The Movie/Show objects in this response are incomplete. For a movie's full details, a
     * call should be made to `/movie/{movie_id}` or `/tv/{tv_id}`.
     */
    @SerializedName("results")
    val movies: List<T>,

    @SerializedName("total_results")
    val totalResults: Int,

    /**
     * The total number of pages of results available.
     */
    @SerializedName("total_pages")
    val totalPages: Int
)