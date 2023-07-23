package com.kagiya.roamio.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kagiya.roamio.data.network.OpenTripMapRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"


@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: OpenTripMapRepository
): ViewModel() {


    init {
        Log.d(TAG, "ViewModelInitialized")

        try{
            val response = viewModelScope.launch{
                repository.getRecommendedPlaces(
                    200000,
                    "-41.4613",
                    "-12.2507",
                   "json",
                    20
                )
            }

            Log.d(TAG, response.toString())
        }
        catch (ex: Exception){
            Log.e(TAG, "Error at getting recomended places", ex)
        }
    }

    fun test(){
        Log.d("TEST", "ViewModelInitialized")
    }
}