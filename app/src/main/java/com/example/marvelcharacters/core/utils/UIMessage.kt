package com.example.marvelcharacters.core.utils

import androidx.annotation.StringRes
import java.util.UUID

sealed class UIMessage(
    val message: String,
    val id: Long,
    @StringRes val stringId: Int? = null
) {
    class Success(
        message: String,
        id: Long = UUID.randomUUID().mostSignificantBits,
        @StringRes stringId: Int? = null
    ) : UIMessage(message, id, stringId)

    class Error(
        message: String,
        id: Long = UUID.randomUUID().mostSignificantBits,
        @StringRes stringId: Int? = null
    ) : UIMessage(message, id, stringId)

    companion object {
        fun fromAppError(appError: AppError): UIMessage? {
            return when (appError) {
                is AppError.JustMessage -> Error(appError.errorMessage)
                is AppError.ResourceError -> Error(message = "", stringId = appError.stringId)
                else -> null
            }
        }
    }

}