package com.kagiya.roamio.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceDetails(
    val address: Address?,
    val image: String?,
    val kinds: String?,
    val name: String?,
    val osm: String?,
    val otm: String?,
    val point: PlacePoint?,
    val preview: Preview?,
    val rate: String?,
    val sources: Sources?,
    val url: String?,
    val wikidata: String?,
    val wikipedia: String?,
    val wikipedia_extracts: WikipediaExtracts?,
    val xid: String?
)