package com.example.carpooling.ui.publish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentPublishSeatsBinding
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class PublishSeatsFragment : Fragment() {

    private lateinit var binding: FragmentPublishSeatsBinding
    private val publishViewModel: PublishViewModel by activityViewModels{
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish_seats, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.apply {
            viewModel = publishViewModel
            lifecycleOwner = viewLifecycleOwner

            btnMinus.setOnClickListener {
                publishViewModel.decreaseAvailableSeats()
            }

            btnPlus.setOnClickListener {
                publishViewModel.increaseAvailableSeats()
            }

            btnToPublishOptions.setOnClickListener {
                val action = PublishSeatsFragmentDirections.toPublishOptions()
                navController.navigate(action)
            }
        }
    }
}