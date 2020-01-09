package codes.terminaether.watchlist.data.repo

import codes.terminaether.watchlist.data.model.ApiResult
import retrofit2.Response
import java.io.IOException

/**
 * Base Repository class which contains convenience methods applicable to all Repositories.
 *
 * Created by terminaether on 2020-01-03.
 */
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): ApiResult<T> {
        return try {
            safeApiResult(call, errorMessage)
        } catch (e: Exception) {
            //Convert API exception to IOException
            ApiResult.Error(IOException(errorMessage, e))
        }
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): ApiResult<T> {
        val response = call.invoke()
        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                //Returns a guaranteed non-null response body
                return ApiResult.Success(responseBody)
            }
        }

        return ApiResult.Error(IOException(errorMessage))
    }

}