package com.kagiya.roamio.ui.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.kagiya.roamio.R
import com.kagiya.roamio.adapters.OnboardingAdapater
import com.kagiya.roamio.databinding.FragmentOnboardingBinding


class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private lateinit var binding: FragmentOnboardingBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)

        setupOnboarding()

        return binding.root
    }


    private fun setupOnboarding(){
        val adapter = OnboardingAdapater(getOnBoardingItems())
        binding.slider.adapter = adapter
        setupDotsIndicator()
    }


    private fun getOnBoardingItems() : List<OnboardingItem>{
        val onboardingItems = listOf(

            OnboardingItem(
                title = R.string.onboarding_title_1,
                description = R.string.onboarding_description_1,
                imageId = R.drawable.onboarding_image_1,
                buttonText = R.string.onboarding_button_1
            ) {
                binding.slider.currentItem += 1
            },

            OnboardingItem(
                title = R.string.onboarding_title_2,
                description = R.string.onboarding_description_2,
                imageId = R.drawable.onboarding_image_2,
                buttonText = R.string.onboarding_button_2
            ) {
                binding.slider.currentItem += 1
            },

            OnboardingItem(
                title = R.string.onboarding_title_3,
                description = R.string.onboarding_description_3,
                imageId = R.drawable.onboarding_image_3,
                buttonText = R.string.onboarding_button_3
            ) {
                setAlreadSawOnboarding()
            },
        )

        return onboardingItems;
    }


    private fun setupDotsIndicator(){
        binding.dotsIndicator.attachTo(binding.slider)
    }

    private fun setAlreadSawOnboarding(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(COMPLETED_ONBOARDING_PREF_NAME, false)
            apply()
        }
    }

    companion object{
        const val COMPLETED_ONBOARDING_PREF_NAME = "COMPLETED_ONBOARDING_PREF"
    }


    data class OnboardingItem(
        @StringRes val title: Int,
        @StringRes val description: Int,
        @DrawableRes val imageId: Int,
        @StringRes val buttonText: Int,
        val onButtonClicked: () -> Unit
    )
}