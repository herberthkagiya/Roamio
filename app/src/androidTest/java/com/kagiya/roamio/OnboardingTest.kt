package com.kagiya.roamio

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kagiya.roamio.ui.fragments.HomeFragment
import com.kagiya.roamio.ui.fragments.OnboardingFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class OnboardingTest {



    @Test
    fun isAlreadySawOnboardingSavedInSharedPreferences(){
        val scenario = launchFragmentInContainer<HomeFragment>()

        scenario.onFragment {
            val sharedPref = it.activity?.getSharedPreferences(
                getString(it.activity!!, R.string.preference_file_key), Context.MODE_PRIVATE
            )

            val savedValueInSharedPreferences = sharedPref?.getBoolean(OnboardingFragment.COMPLETED_ONBOARDING_PREF_NAME, true)

            val isSaved = savedValueInSharedPreferences != null

            assertEquals(isSaved, true)
        }
    }
}