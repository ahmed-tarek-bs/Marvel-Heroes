package com.example.marvelcharacters.data.dto

import com.google.gson.annotations.SerializedName

data class AppearancesDTO(

    @SerializedName("available") val available: Int? = null,
    @SerializedName("collectionURI") val collectionURI: String? = null,
    @SerializedName("items") val items: List<LinkItemDTO>? = null,
    @SerializedName("returned") val returned: Int? = null

) {
    fun getAppearancesList(): List<String>? {
        return items?.mapNotNull { it.getName() }
    }
}