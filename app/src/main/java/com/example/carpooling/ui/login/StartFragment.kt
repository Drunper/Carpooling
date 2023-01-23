package com.example.carpooling.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.databinding.FragmentStartBinding
import com.example.carpooling.utils.SessionManager
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.btnStartLogin.setOnClickListener {
            val action = StartFragmentDirections.goToLogin()
            navController.navigate(action)
        }
    }
}