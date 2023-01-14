package com.example.carpooling.ui.profile

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.carpooling.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}