package com.example.carpooling.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentPassengerOldRideBinding

class PassengerOldRideFragment : Fragment() {
    private lateinit var binding: FragmentPassengerOldRideBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_passenger_old_ride, container, false)
        return binding.root
    }
}