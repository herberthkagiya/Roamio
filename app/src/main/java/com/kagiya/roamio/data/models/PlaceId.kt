package com.kagiya.roamio.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceId(
    val xid: String?,
    val name: String?,
    val dist: Double?,
    val rate: Int?,
    val wikidata: String?,
    val kinds: String?,
    val point: PlacePoint?,
)