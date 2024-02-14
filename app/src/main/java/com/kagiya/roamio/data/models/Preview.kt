package com.kagiya.roamio.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Preview(
    val height: Int?,
    val source: String?,
    val width: Int?
)