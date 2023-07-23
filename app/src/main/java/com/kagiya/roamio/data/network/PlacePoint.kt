package com.kagiya.roamio.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlacePoint(
    val lat: Double,
    val lon: Double
)