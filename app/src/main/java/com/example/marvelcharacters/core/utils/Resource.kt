package com.example.marvelcharacters.core.utils

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()

    class Error<T>(val appError: AppError) : Resource<T>()
}


suspend fun <T : Any> Resource<T>.onResult(
    executable: suspend () -> Unit
): Resource<T> = apply {
    executable()
}

suspend fun <T : Any> Resource<T>.onSuccess(
    executable: suspend (T) -> Unit
): Resource<T> = apply {
    if (this is Resource.Success) {
        executable(data)
    }
}

suspend fun <T : Any> Resource<T>.onError(
    executable: suspend (error: AppError) -> Unit
): Resource<T> = apply {
    if (this is Resource.Error) {
        executable(appError)
    }
}