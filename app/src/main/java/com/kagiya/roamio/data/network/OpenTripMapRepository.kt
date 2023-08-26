package com.kagiya.roamio.data.network

import android.util.Log
import com.kagiya.roamio.api.OpenTripMapService
import retrofit2.http.Query
import javax.inject.Inject

private const val TAG = "OpenTripMapRepository"


class OpenTripMapRepository @Inject constructor(
    private val service: OpenTripMapService
){

    suspend fun getRecommendedPlacesIds(
        radius: Int,
        longitude: String,
        latitude: String,
        rate: String,
        format: String,
        limit: Int
    ): List<PlaceId> {

        return service.fetchRecommendedPlacesIds(
            radius,
            longitude,
            latitude,
            rate,
            format,
            limit
        )
    }


    suspend fun getPlaceDetails(id: String) : PlaceDetails{
        return service.getPlaceDetails(id)
    }

    suspend fun fetchPlaceDetails(placeIds: List<PlaceId>) : List<PlaceDetails>{

        var placesDetails: List<PlaceDetails> = emptyList()

        try{
            placeIds.forEach{ place ->
                 placesDetails += getPlaceDetails(place.xid.toString())
            }
        }
        catch (ex: Exception){
            Log.d(TAG, "Error: failed at fetching places details")
        }

        return placesDetails
    }
}