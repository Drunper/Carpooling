package com.example.carpooling.ui.profile

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import androidx.preference.PreferenceFragmentCompat
import com.example.carpooling.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val r: Resources = resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            48.0f,
            r.displayMetrics
        )
        Log.d("msg", px.toString())
        val layoutParams = (listView.layoutParams as FrameLayout.LayoutParams)
        layoutParams.setMargins(0, px.toInt(), 0, 0)
        return view
    }
}