package com.example.marvelcharacters.data.dto

import com.google.gson.annotations.SerializedName

data class ThumbnailDTO(
    @SerializedName("path") var path: String? = null,
    @SerializedName("extension") var extension: String? = null
)