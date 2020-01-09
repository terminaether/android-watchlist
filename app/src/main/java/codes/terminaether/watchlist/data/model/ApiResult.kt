package codes.terminaether.watchlist.data.model

/**
 * Used to signal the status of an API call to the UI layer.
 *
 * Created by terminaether on 2020-01-03.
 */
sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
}