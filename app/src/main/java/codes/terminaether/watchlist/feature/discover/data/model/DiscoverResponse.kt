package codes.terminaether.watchlist.feature.discover.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class to describe the response given by TMDb's `/discover/movie` endpoint.
 *
 * Created by terminaether on 2020-01-02.
 */
data class DiscoverResponse<Media>(
    /**
     * This response's page number.
     */
    val page: Int,

    /**
     * A list of the requested Movies or Shows.
     *
     * Note: The Media objects in this response are incomplete. For an item's full details, a
     * call should be made to `/movie/{movie_id}` or `/tv/{tv_id}`.
     */
    val results: List<Media>,

    @SerializedName("total_results")
    val totalResults: Int,

    /**
     * The total number of pages of results available.
     */
    @SerializedName("total_pages")
    val totalPages: Int
)