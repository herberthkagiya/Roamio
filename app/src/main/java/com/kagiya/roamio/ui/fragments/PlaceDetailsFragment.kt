package com.kagiya.roamio.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.kagiya.roamio.R
import com.kagiya.roamio.databinding.FragmentPlaceDetailsBinding
import com.kagiya.roamio.utils.isNetworkAvailable
import com.kagiya.roamio.viewmodels.PlaceDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

private const val TAG = "PlaceDetailsFragment"

@AndroidEntryPoint
class PlaceDetailsFragment : Fragment() {

    private val args: PlaceDetailsFragmentArgs by navArgs()

    private val viewModel: PlaceDetailsViewModel by viewModels()

    private lateinit var binding: FragmentPlaceDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isNetworkAvailable(context)) {
            viewModel.getPlaceDetails(args.placeId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)

        binding.shimmerContainer.startShimmer()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.placeDetails?.let{

                        val details = it[0]

                        binding.placeImage.load(details.preview?.source)
                        binding.placeName.text = details.name
                        binding.placeDescription.text = details.wikipedia_extracts?.text

                        stopShimmer()
                    }
                }
            }
        }
    }

    private fun stopShimmer(){
        binding.shimmerContainer.stopShimmer()
        binding.shimmerContainer.visibility = View.GONE
    }
}