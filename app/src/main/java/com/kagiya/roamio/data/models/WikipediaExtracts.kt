package com.kagiya.roamio.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WikipediaExtracts(
    val html: String?,
    val text: String?,
    val title: String?
)