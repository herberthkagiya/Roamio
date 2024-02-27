package com.kagiya.roamio.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
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

private const val MAX_PLACE_DESCRIPTION_LINES = 5

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

                        binding.showMoreLessDescription.setOnClickListener{
                            setupShowMoreOrLessDescriptionButton()
                        }

                        stopShimmer()
                        binding.showMap.visibility = View.VISIBLE
                        binding.showMoreLessDescription.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun stopShimmer(){
        binding.shimmerContainer.stopShimmer()
        binding.shimmerContainer.visibility = View.GONE
    }

    private fun setupShowMoreOrLessDescriptionButton(){

        if(viewModel.uiState.value.isDescriptionExpanded){
            binding.placeDescription.maxLines = MAX_PLACE_DESCRIPTION_LINES
            viewModel.uiState.value.isDescriptionExpanded = false
            changeTextFromShowMoreLessButton()
        }
        else{
            binding.placeDescription.maxLines = Int.MAX_VALUE
            viewModel.uiState.value.isDescriptionExpanded = true
            changeTextFromShowMoreLessButton()
        }
    }

    private fun changeTextFromShowMoreLessButton(){

        if(viewModel.uiState.value.isDescriptionExpanded){
            val showLessText = context?.getText(R.string.show_less_description)
            binding.showMoreLessDescription.text = showLessText

            val ic = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_show_less) }
            binding.showMoreLessDescription.setCompoundDrawablesWithIntrinsicBounds(null, null, ic, null)
        }
        else{
            val showMoreText = context?.getText(R.string.show_more_description)
            binding.showMoreLessDescription.text = showMoreText

            val ic = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_show_more) }
            binding.showMoreLessDescription.setCompoundDrawablesWithIntrinsicBounds(null, null, ic, null)
        }
    }
}