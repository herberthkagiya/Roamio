package com.kagiya.roamio.data.network

import com.squareup.moshi.JsonClass
import org.json.JSONArray

@JsonClass(generateAdapter = true)
data class RecommendedPlacesResponse (
    val result : List<Place>
)