package com.example.carpooling.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentModifyProfileSuccessBinding

class ModifyProfileSuccessFragment : Fragment() {

    private lateinit var binding: FragmentModifyProfileSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_profile_success, container, false)
        return binding.root
    }
}