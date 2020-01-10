package codes.terminaether.watchlist.data.model

/**
 * Generic class for managing state between data and UI layers.
 * With thanks to Conor.
 *
 * Created by terminaether on 2020-01-10.
 */
sealed class UiState<out T> {
    //Initial state
    object None : UiState<Nothing>()

    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    class Error(val exception: Throwable) : UiState<Nothing>()
}