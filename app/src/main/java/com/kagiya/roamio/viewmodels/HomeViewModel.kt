package com.kagiya.roamio.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kagiya.roamio.data.PlacesRepository
import com.kagiya.roamio.data.models.PlaceDetails
import com.kagiya.roamio.data.models.PlaceId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "HomeViewModel"


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PlacesRepository
): ViewModel() {


    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            recommendedPlaceIds = emptyList(),
            recommendedPlacesDetails = emptyList(),
        )
    )
    val uiState  = _uiState.asStateFlow()


    fun fetchRecommendedPlacesIds(
        longitude: String,
        latitude: String
    ){
        var placeIds: List<PlaceId>? = emptyList()

        try{
            viewModelScope.launch{
                placeIds = repository.getRecommendedPlacesIds(
                    200000,
                    longitude,
                    latitude,
                    "3h",
                    "json",
                    5
                )


                _uiState.update { oldState ->
                    oldState.copy(
                        recommendedPlaceIds = placeIds
                    )
                }
            }
        }
        catch (ex: Exception){
            Log.e(TAG, "Error: getting recomended places failed", ex)
        }
    }

    fun fetchRecommendedPlacesDetails(){

        var response: List<PlaceDetails>? = emptyList()

        try{
            viewModelScope.launch{

                response = uiState.value.recommendedPlaceIds?.let { repository.fetchPlaceDetails(it) }

                _uiState.update { oldState ->
                    oldState.copy(
                        recommendedPlacesDetails = response
                    )
                }
            }
        }
        catch (ex: Exception){
            Log.e(TAG, "Error: getting recomended places failed", ex)
        }
    }

    data class UiState(
        val recommendedPlaceIds: List<PlaceId>?,
        val recommendedPlacesDetails: List<PlaceDetails>?,
    )
}