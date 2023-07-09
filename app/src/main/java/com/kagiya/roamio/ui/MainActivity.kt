package com.kagiya.roamio.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kagiya.roamio.R
import com.kagiya.roamio.databinding.MainActivityBinding
import com.kagiya.roamio.ui.fragments.OnboardingFragment


class MainActivity : FragmentActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }


    private fun setOnboardingIfNecessary(){
        if(IsFirstTimeLaunchingTheApp()){
            showOnboardingScreen()
        }
    }

    private fun showOnboardingScreen(){
        val navController = supportFragmentManager.findFragmentById(R.id.fragment_container)?.findNavController()

        navController?.navigate(R.id.onboardingFragment)
    }

    private fun IsFirstTimeLaunchingTheApp() : Boolean{
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        return sharedPref.getBoolean(OnboardingFragment.COMPLETED_ONBOARDING_PREF_NAME, true)
    }
}
