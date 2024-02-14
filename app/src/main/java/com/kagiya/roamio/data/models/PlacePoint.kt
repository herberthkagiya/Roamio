package com.kagiya.roamio.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlacePoint(
    val lat: Double,
    val lon: Double
)