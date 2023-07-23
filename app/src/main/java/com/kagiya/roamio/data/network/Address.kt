package com.kagiya.roamio.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    val city: String?,
    val country: String?,
    val country_code: String?,
    val house: String?,
    val house_number: String?,
    val pedestrian: String?,
    val postcode: String?,
    val state: String?
)