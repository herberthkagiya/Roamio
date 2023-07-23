package com.kagiya.roamio.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Place(
    val xid: String?,
    val name: String?,
    val dist: Double?,
    val rate: Int?,
    val wikidata: String?,
    val kinds: String?,
    val point: PlacePoint?,
)