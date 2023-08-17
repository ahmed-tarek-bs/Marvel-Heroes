package com.example.marvelcharacters.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageUrl(
    val portraitUrl: String? = null,
    val squareUrl: String? = null
) : Parcelable
