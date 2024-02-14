package com.kagiya.roamio.data

import com.kagiya.roamio.api.OpenTripMapService
import com.kagiya.roamio.data.models.PlaceDetails
import com.kagiya.roamio.data.models.PlaceId
import javax.inject.Inject

class PlacesRepository @Inject constructor(
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


    suspend fun getPlaceDetails(id: String) : PlaceDetails {
        return service.getPlaceDetails(id)
    }

    suspend fun fetchPlaceDetails(placeIds: List<PlaceId>) : List<PlaceDetails>{

        var placesDetails: List<PlaceDetails> = emptyList()


        placeIds.forEach{ place ->
            placesDetails += getPlaceDetails(place.xid.toString())
        }


        return placesDetails
    }
}