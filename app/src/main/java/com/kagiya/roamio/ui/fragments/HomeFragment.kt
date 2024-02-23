package com.kagiya.roamio.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kagiya.roamio.adapters.RecommendedAdapter
import com.kagiya.roamio.databinding.FragmentHomeBinding
import com.kagiya.roamio.utils.GPSUtils
import com.kagiya.roamio.utils.isNetworkAvailable
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
        loadRecommendedPlacesIds()
    }


    override fun onStart() {
        super.onStart()

        if(!viewModel.isRecommendedAlreadyLoaded()){
            getCurrentLocation()
            loadRecommendedPlacesIds()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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


                    if(isNetworkAvailable(context) && !viewModel.isRecommendedAlreadyLoaded()){
                        viewModel.fetchRecommendedPlacesDetails()
                    }

                    binding.recommendedRecyclerView.adapter =
                        uiState.recommendedPlacesDetails?.let {
                            RecommendedAdapter(it)
                        }

                    checkIfRecommendedWasLoadedAndHideShimmer()
                }
            }
        }

        showMessageIfThereIsNoInternetOrLocation()
    }

    private fun showMessageIfThereIsNoInternetOrLocation() {
        if((!isNetworkAvailable(context)) || (gpsUtils.getLatitude() == null || gpsUtils.getLongitude() == null)){
            binding.errorMessageContainer.visibility = View.VISIBLE
            binding.recommendedRecyclerView.visibility = View.GONE
            binding.shimmerContainer.visibility = View.GONE
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

    private fun loadRecommendedPlacesIds(){
        val longitude = gpsUtils.getLongitude()
        val latitude = gpsUtils.getLatitude()

        if((longitude != null && latitude != null) && isNetworkAvailable(context)){
            viewModel.fetchRecommendedPlacesIds(longitude, latitude)
        }
    }

}