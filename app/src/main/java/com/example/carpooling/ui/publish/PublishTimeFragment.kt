package com.example.carpooling.ui.publish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentPublishTimeBinding
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PublishTimeFragment : Fragment() {
    private lateinit var binding: FragmentPublishTimeBinding
    private val publishViewModel: PublishViewModel by navGraphViewModels(R.id.publish_nav_graph){
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish_time, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        publishViewModel.timeFormState.observe(viewLifecycleOwner) {
            if (it)
                binding.btnToPublishSeats.isEnabled = true
        }

        binding.apply {
            viewModel = publishViewModel
            lifecycleOwner = viewLifecycleOwner

            fieldDepartureTime.setOnClickListener {
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(0)
                        .build()

                picker.addOnPositiveButtonClickListener {
                    val time = LocalTime.of(picker.hour, picker.minute)
                    val dtf = DateTimeFormatter.ofPattern("HH:mm")
                    publishViewModel.setDepartureTime(time.format(dtf))
                }
                picker.show(parentFragmentManager, "timepicker")

                /*val action = PublishTimeFragmentDirections.insertTime(true)
                navController.navigate(action)*/
            }

            fieldArrivalTime.setOnClickListener {
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(0)
                        .build()

                picker.addOnPositiveButtonClickListener {
                    val time = LocalTime.of(picker.hour, picker.minute)
                    val dtf = DateTimeFormatter.ofPattern("HH:mm")
                    publishViewModel.setArrivalTime(time.format(dtf))
                }
                picker.show(parentFragmentManager, "timepicker")
            }

            btnToPublishSeats.setOnClickListener {
                val action = PublishTimeFragmentDirections.toPublishSeats()
                navController.navigate(action)
            }
        }
    }
}