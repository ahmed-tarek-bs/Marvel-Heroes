package com.example.marvelcharacters.data.dto

import com.example.marvelcharacters.domain.model.MarvelHero
import com.google.gson.annotations.SerializedName

data class HeroDTO(

    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("thumbnail") val thumbnail: ThumbnailDTO? = null,
    @SerializedName("resourceURI") val resourceURI: String? = null,
    @SerializedName("comics") val comics: AppearancesDTO? = null,
    @SerializedName("series") val series: AppearancesDTO? = null,
    @SerializedName("stories") val stories: AppearancesDTO? = null,
    @SerializedName("events") val events: AppearancesDTO? = null,
    @SerializedName("urls") val urls: List<UrlDTO>? = null

) {

    fun mapToMarvelHero(): MarvelHero {
        return MarvelHero(
            id = id,
            name = name,
            description = description,
            imgUrl = thumbnail?.getImageUrl(),
            comics = comics?.getAppearancesList(),
            series = series?.getAppearancesList(),
            stories = stories?.getAppearancesList(),
            events = events?.getAppearancesList()
        )
    }

}













