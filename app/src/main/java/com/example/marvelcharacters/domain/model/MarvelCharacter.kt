package com.example.marvelcharacters.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelCharacter(
    val id: Int?,
    val name: String?,
    val description: String?,
    val imgUrl: String?,
    val comics: List<String>?,
    val series: List<String>?,
    val stories: List<String>?,
    val events: List<String>?
) : Parcelable













