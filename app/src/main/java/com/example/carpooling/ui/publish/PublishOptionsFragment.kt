package com.example.carpooling.ui.publish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentPublishOptionsBinding
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class PublishOptionsFragment : Fragment() {

    private lateinit var binding: FragmentPublishOptionsBinding
    private val publishViewModel: PublishViewModel by navGraphViewModels(R.id.publish_nav_graph){
        ViewModelFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish_options, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        binding.apply {
            viewModel = publishViewModel
            lifecycleOwner = viewLifecycleOwner
            btnToPublishNotes.setOnClickListener {
                val action = PublishOptionsFragmentDirections.toPublishNotes()
                navController.navigate(action)
            }
        }
    }
}