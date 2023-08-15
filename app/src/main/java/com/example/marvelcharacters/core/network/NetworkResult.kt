package com.example.marvelcharacters.core.network

import com.example.marvelcharacters.core.utils.AppError
import com.example.marvelcharacters.core.utils.NetworkErrors
import com.example.marvelcharacters.core.utils.Resource


sealed class NetworkResult<out T : Any> {
    class ApiSuccess<out T : Any>(val successData: T) : NetworkResult<T>()
    class ApiError(val error: NetworkErrors) : NetworkResult<Nothing>()
}

suspend fun <T : Any> NetworkResult<T>.onResult(
    executable: suspend () -> Unit
): NetworkResult<T> = apply {
    executable()
}

suspend fun <T : Any> NetworkResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.ApiSuccess<T>) {
        executable(successData)
    }
}

suspend fun <T : Any> NetworkResult<T>.onError(
    executable: suspend (error: NetworkErrors) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.ApiError) {
        executable(error)
    }
}

fun <T : Any, V> NetworkResult.ApiSuccess<T>.createSuccessResource(mapper: (T) -> V): Resource.Success<V> {
    return Resource.Success(mapper(this.successData))
}

fun <T> NetworkResult.ApiError.createErrorResource(): Resource.Error<T> {
    return Resource.Error(appError = AppError.JustMessage(this.error.message))
}