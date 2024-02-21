package com.kagiya.roamio.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.kagiya.roamio.adapters.RecommendedAdapter
import com.kagiya.roamio.databinding.FragmentHomeBinding
import com.kagiya.roamio.utils.GPSUtils
import com.kagiya.roamio.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val gpsUtils = GPSUtils.instance


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        getCurrentLocation()
        setRecommendedPlaces()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recommendedRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        //Start Shimmer
        binding.shimmerContainer.startShimmer()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect{ uiState ->

                    viewModel.fetchRecommendedPlacesDetails()

                    binding.recommendedRecyclerView.adapter =
                        uiState.recommendedPlacesDetails?.let {
                            RecommendedAdapter(it)
                        }

                    checkIfRecommendedWasLoadedAndHideShimmer()
                }
            }
        }
    }

    private fun checkIfRecommendedWasLoadedAndHideShimmer() {
        if(viewModel.uiState.value.recommendedPlacesDetails?.isEmpty() == false){
            binding.shimmerContainer.stopShimmer()
            //Hide Shimmer view
            binding.shimmerContainer.visibility = View.GONE
        }
    }


    private fun getCurrentLocation(){
        gpsUtils.initPermissions(activity)
        gpsUtils.findDeviceLocation(activity as Activity)
    }

    private fun setRecommendedPlaces(){
        val longitude = gpsUtils.getLongitude()
        val latitude = gpsUtils.getLatitude()

        if(longitude != null && latitude != null){
            viewModel.fetchRecommendedPlacesIds(longitude, latitude)
        }
        viewModel.fetchRecommendedPlacesDetails()
    }
}