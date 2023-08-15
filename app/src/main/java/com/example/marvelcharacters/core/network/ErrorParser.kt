package com.example.marvelcharacters.core.network

import com.example.marvelcharacters.core.utils.NetworkErrors
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

internal object ErrorParser {

    fun parseHttpError(response: Response<*>): NetworkErrors {
        val errorResponse = parseErrorResponse(response)
        errorResponse?.let {

            var errorMessage = it.errorMessage

            if (errorMessage?.isBlank() == true)
                errorMessage = "Unexpected Error"

            return if (response.code() == 401)
                NetworkErrors.AuthError
            else
                NetworkErrors.NetworkError(message = errorMessage!!)
        }

        return if (response.code() == 401)
            NetworkErrors.AuthError
        else
            NetworkErrors.Undefined("Unexpected Error")

    }

    private fun parseErrorResponse(response: Response<*>?): ErrorDTO? {
        response?.errorBody().toStringOrNull()?.let {
            return tryOrNull { Gson().fromJson(it, ErrorDTO::class.java) }
        }

        return null
    }

    private fun ResponseBody?.toStringOrNull() =
        try {
            this?.string()
        } catch (e: IOException) {
            null
        }

    private fun <T> tryOrNull(action: () -> T) =
        try {
            action()
        } catch (t: Throwable) {
            null
        }

}