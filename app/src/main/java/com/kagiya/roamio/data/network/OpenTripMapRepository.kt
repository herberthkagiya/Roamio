package com.kagiya.roamio.data.network

import android.util.Log
import com.kagiya.roamio.api.OpenTripMapService
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject


class OpenTripMapRepository @Inject constructor(
    private val service: OpenTripMapService
){

    suspend fun getRecommendedPlaces(
        radius: Int,
        longitude: String,
        latitude: String,
        format: String,
        limit: Int
    ): List<Place> {

        return service.getRecommendedPlaces(
            radius,
            longitude,
            latitude,
            format,
            limit
        )
    }

}