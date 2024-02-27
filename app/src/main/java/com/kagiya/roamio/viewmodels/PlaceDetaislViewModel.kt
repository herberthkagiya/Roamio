package com.kagiya.roamio.viewmodels

import android.util.Log
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

private const val TAG = "PlaceDetailsViewModel"

@HiltViewModel
class PlaceDetailsViewModel @Inject constructor(
    private val repository: PlacesRepository
) : ViewModel() {

    private var _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(null)
    )

    val uiState = _uiState.asStateFlow()


    fun getPlaceDetails(placeId: String){
        var newPlaceDetails: List<PlaceDetails> = emptyList()

        try{
            viewModelScope.launch{

                newPlaceDetails = listOf(repository.getPlaceDetails(placeId))

                _uiState.update { oldState ->
                    oldState.copy(
                        placeDetails = newPlaceDetails
                    )
                }
            }

            Log.d("Place", _uiState.value.placeDetails.toString())
        }
        catch (ex: Exception){
            Log.e(TAG, "Error: getting place details", ex)
        }

    }
}

data class UiState(
    val placeDetails: List<PlaceDetails>?,
    var isDescriptionExpanded: Boolean = false
)