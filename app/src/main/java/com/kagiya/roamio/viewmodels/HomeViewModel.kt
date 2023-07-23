package com.kagiya.roamio.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kagiya.roamio.data.network.OpenTripMapRepository
import com.kagiya.roamio.data.network.Place
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: OpenTripMapRepository
): ViewModel() {


    private val _uiState: MutableStateFlow<UiState?> = MutableStateFlow(UiState(emptyList()))
    val uiState  = _uiState.asStateFlow()


    fun fetchRecommendedPlaces(){
        var response: List<Place>? = emptyList()

        try{
            viewModelScope.launch{
                response = repository.getRecommendedPlaces(
                    200000,
                    "-41.4613",
                    "-12.2507",
                    "json",
                    5
                )

                _uiState.update { oldState ->
                    oldState?.copy(
                        recommendedPlaces = response
                    )
                }
            }
        }
        catch (ex: Exception){
            Log.e(TAG, "Error: getting recomended places failed", ex)
        }
    }


    data class UiState(
        val recommendedPlaces: List<Place>?
    )
}