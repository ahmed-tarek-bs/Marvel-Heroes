package com.example.marvelcharacters.data.dto

import com.google.gson.annotations.SerializedName

data class LinkItemDTO(

    @SerializedName("resourceURI") val resourceURI: String? = null,
    @SerializedName("name") val linkName: String? = null,
    @SerializedName("type") val type: String? = null

) {
    fun getName(): String? {
        return linkName
    }
}