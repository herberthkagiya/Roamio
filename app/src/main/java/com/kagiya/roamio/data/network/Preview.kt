package com.kagiya.roamio.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Preview(
    val height: Int?,
    val source: String?,
    val width: Int?
)