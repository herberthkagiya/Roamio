package com.kagiya.roamio.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sources(
    val attributes: List<String>?,
    val geometry: String?
)