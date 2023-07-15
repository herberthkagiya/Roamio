package com.kagiya.roamio.ui

import android.content.Context
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kagiya.roamio.R
import com.kagiya.roamio.databinding.MainActivityBinding
import com.kagiya.roamio.ui.fragments.OnboardingFragment


class MainActivity : FragmentActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnboardingIfNecessary()

        setupBottomNagationMenu()
    }


    private fun setOnboardingIfNecessary(){
        if(isFirstTimeLaunchingTheApp()){
            showOnboardingScreen()
        }
    }

    private fun isFirstTimeLaunchingTheApp() : Boolean{
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        return sharedPref.getBoolean(OnboardingFragment.COMPLETED_ONBOARDING_PREF_NAME, true)
    }

    private fun showOnboardingScreen(){
        val navController = supportFragmentManager.findFragmentById(R.id.fragment_container)?.findNavController()

        navController?.navigate(R.id.onboardingFragment)
    }


    private fun setupBottomNagationMenu(){
        val navController = this.findNavController(R.id.fragment_container)
        binding.bottomNavigationMenu.setupWithNavController(navController)

        binding.bottomNavigationMenu.itemIconTintList = null
        removeMenuDependingOnFragmentThatIsOnScreen()
    }


    private fun removeMenuDependingOnFragmentThatIsOnScreen(){
        val bottomNavigationView = binding.bottomNavigationMenu
        val navController = findNavController(R.id.fragment_container)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id){
                R.id.onboardingFragment -> bottomNavigationView.visibility = View.GONE
                else ->  bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}
