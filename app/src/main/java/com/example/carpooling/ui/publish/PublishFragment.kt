package com.example.carpooling.ui.publish

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentPublishBinding
import com.example.carpooling.utils.getString
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class PublishFragment : Fragment() {

    private lateinit var binding: FragmentPublishBinding
    private val publishViewModel: PublishViewModel by navGraphViewModels(R.id.publish_nav_graph){
        ViewModelFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        publishViewModel.apply {
            locationFormState.observe(viewLifecycleOwner) {
                if (it)
                    binding.btnToPublishDate.isEnabled = true
            }

            from.observe(viewLifecycleOwner) {
                binding.fieldFrom.text = it.getString()
            }

            to.observe(viewLifecycleOwner) {
                binding.fieldTo.text = it.getString()
            }
        }

        binding.apply {
            fieldFrom.setOnClickListener {
                val action = PublishFragmentDirections.insertLocation(true)
                navController.navigate(action)
            }
            fieldTo.setOnClickListener {
                val action = PublishFragmentDirections.insertLocation(false)
                navController.navigate(action)
            }
            btnToPublishDate.setOnClickListener {
                val action = PublishFragmentDirections.toPublishDate()
                navController.navigate(action)
            }
        }
    }
}