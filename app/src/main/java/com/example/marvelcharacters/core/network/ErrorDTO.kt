package com.example.marvelcharacters.core.network

import com.google.gson.annotations.SerializedName

data class ErrorDTO(
    @SerializedName("message") val errorMessage: String? = null,
    @SerializedName("code") val code: String? = null
)

