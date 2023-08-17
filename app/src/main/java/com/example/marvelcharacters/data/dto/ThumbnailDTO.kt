package com.example.marvelcharacters.data.dto

import com.example.marvelcharacters.domain.model.ImageUrl
import com.google.gson.annotations.SerializedName

data class ThumbnailDTO(
    @SerializedName("path") var path: String? = null,
    @SerializedName("extension") var extension: String? = null
) {

    fun mapToImageUrl(): ImageUrl {
        return ImageUrl(
            portraitUrl = path?.plus("/portrait_uncanny.${extension ?: "jpg"}"),
            squareUrl = path?.plus("/standard_fantastic.${extension ?: "jpg"}"),
        )

    }

}