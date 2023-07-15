package com.kagiya.roamio.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kagiya.roamio.databinding.ItemOnboardingBinding
import com.kagiya.roamio.ui.fragments.OnboardingFragment


class OnboardingAdapater(
    private val items: List<OnboardingFragment.OnboardingItem>
): RecyclerView.Adapter<OnboardingHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOnboardingBinding.inflate(inflater, parent, false)
        return OnboardingHolder(binding)
    }


    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: OnboardingHolder, position: Int) {
        holder.bind(items[position])
    }
}


class OnboardingHolder(val binding: ItemOnboardingBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: OnboardingFragment.OnboardingItem){

        binding.backgroundImage.setImageResource(item.imageId)
        binding.title.setText(item.title)
        binding.description.setText(item.description)
        binding.button.setText(item.buttonText)

        binding.button.setOnClickListener{
            item.onButtonClicked()
        }
    }
}