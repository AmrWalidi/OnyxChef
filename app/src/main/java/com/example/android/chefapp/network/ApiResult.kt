package com.example.android.chefapp.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

sealed interface ApiResult<T>
class ApiSuccess<T>(val body: T) : ApiResult<T>
class ApiError<T>(val code: Int?, val message: String?) : ApiResult<T>
class ApiLoading<T> : ApiResult<T>

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
) = try {
    val response: Response<T> = withContext(Dispatchers.IO) {
        execute()
    }
    val body = response.body()
    if (response.isSuccessful && body != null) {
        ApiSuccess(body)
    } else {
        ApiError(code = response.code(), message = response.message())
    }
} catch (e: HttpException) {
    ApiError(code = e.code(), message = e.message())
} catch (e: Throwable) {
    ApiError(message = e.message, code = null)
}


suspend fun <T> ApiResult<T>.onSuccess(
    executable: suspend (T) -> Unit
) = apply {
    if (this is ApiSuccess<T>) {
        executable(body)
    }
}

suspend fun <T> ApiResult<T>.onLoading(
    executable: suspend () -> Unit
): ApiResult<T> = apply {
    if (this is ApiLoading) {
        executable()
    }
}

suspend fun <T> ApiResult<T>.onError(
    executable: suspend (errorMsg: String) -> Unit
): ApiResult<T> = apply {
    if (this is ApiError<T>) {
        executable(message.orEmpty())
    }
}