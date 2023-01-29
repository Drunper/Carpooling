package com.example.carpooling.ui.publish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentPublishDatetimeBinding
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class PublishDatetimeFragment : Fragment() {

    private lateinit var binding: FragmentPublishDatetimeBinding
    private val publishViewModel: PublishViewModel by activityViewModels{
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish_datetime, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = publishViewModel
        }

        val navController = findNavController()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnPublishDate.setOnClickListener{
            navController.navigate(R.id.publishDatePickerFragment)
        }

        binding.btnPublishTime.setOnClickListener{
            navController.navigate(R.id.publishTimePickerFragment)
        }

        binding.btnPublishDatetimeNext.setOnClickListener {
            val action = PublishDatetimeFragmentDirections.toPublishSeats()
            navController.navigate(action)
        }
    }
}