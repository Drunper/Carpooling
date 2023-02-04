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
import com.example.carpooling.databinding.FragmentPublishDateBinding
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PublishDateFragment : Fragment() {

    private lateinit var binding: FragmentPublishDateBinding
    private val publishViewModel: PublishViewModel by activityViewModels{
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish_date, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        publishViewModel.date.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.btnToPublishTime.isEnabled = true
            }
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = publishViewModel

            fieldDate.setOnClickListener {
                val picker = MaterialDatePicker.Builder
                    .datePicker()
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

                picker.addOnPositiveButtonClickListener { selection ->
                    val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(selection), TimeZone.getDefault().toZoneId())
                    val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    publishViewModel.setDate(date.format(dtf))
                }

                picker.show(parentFragmentManager, "datepicker")
            }

            btnToPublishTime.setOnClickListener {
                val action = PublishDateFragmentDirections.toPublishTime()
                navController.navigate(action)
            }
        }
    }
}