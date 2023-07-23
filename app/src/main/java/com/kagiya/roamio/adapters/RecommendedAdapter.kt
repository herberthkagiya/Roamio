package com.kagiya.roamio.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kagiya.roamio.R
import com.kagiya.roamio.data.network.PlaceDetails
import com.kagiya.roamio.databinding.ListItemRecommendedPlaceBinding

class RecommendedAdapter(
    private val recommendedPlaces: List<PlaceDetails>
) : RecyclerView.Adapter<RecommendedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRecommendedPlaceBinding.inflate(inflater, parent, false)
        return RecommendedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.bind(recommendedPlaces[position])
    }

    override fun getItemCount(): Int {
        return recommendedPlaces.size
    }
}


class RecommendedViewHolder (
    private val binding : ListItemRecommendedPlaceBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(recommendedPlace : PlaceDetails){

        if(recommendedPlace.image == null){
            binding.placeImageBackground.setImageResource(R.drawable.onboarding_image_2)
        }
        else{
            binding.placeImageBackground.load(recommendedPlace.image)
        }

        binding.placeName.text = recommendedPlace.name
    }
}
