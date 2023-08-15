package com.example.marvelcharacters.core.network

import com.example.marvelcharacters.core.utils.NetworkErrors
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()

        if (response.isSuccessful) {

            return if (body != null)
                NetworkResult.ApiSuccess(body)
            else
                getError(response)

        } else {
            getError(response)
        }
    } catch (e: HttpException) {
        NetworkResult.ApiError(
            NetworkErrors.NetworkError(code = e.code(), message = "Network Error ${e.code()}")
        )
    } catch (e: Throwable) {
        NetworkResult.ApiError(NetworkErrors.Undefined(message = e.message ?: "Network Exception"))
    }
}

suspend fun getError(response: Response<*>): NetworkResult.ApiError {
    return NetworkResult.ApiError(ErrorParser.parseHttpError(response))
}