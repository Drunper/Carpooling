package com.example.carpooling.ui.activeride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentSuccessBookingBinding

class SuccessBookingFragment : Fragment() {

    private lateinit var binding: FragmentSuccessBookingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding: FragmentSuccessBookingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_success_booking, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
}