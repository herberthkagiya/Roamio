package com.kagiya.roamio.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecommendedPlacesResponse (
    val result : List<PlaceId>
)