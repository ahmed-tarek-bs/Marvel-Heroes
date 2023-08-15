package com.example.marvelcharacters.core.utils

import androidx.annotation.StringRes

sealed class NetworkErrors(val code: Int? = null, val message: String) {
    class NetworkError(code: Int? = null, message: String) : NetworkErrors(code, message)
    class Undefined(message: String) : NetworkErrors(message = message)

    object AuthError : NetworkErrors(message = "Auth Error")
}

sealed class AppError {
    data class JustMessage(val errorMessage: String) : AppError()
    object UnExpectedError : AppError()
    object IgnoredError : AppError()
    class ResourceError(@StringRes val stringId: Int) : AppError()
}