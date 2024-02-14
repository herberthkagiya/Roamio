package com.kagiya.roamio.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecommendedPlacesResponse (
    val result : List<PlaceId>
)