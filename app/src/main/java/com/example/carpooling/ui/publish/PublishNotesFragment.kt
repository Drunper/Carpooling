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
import com.example.carpooling.databinding.FragmentPublishNotesBinding
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class PublishNotesFragment : Fragment() {

    private lateinit var binding: FragmentPublishNotesBinding
    private val publishViewModel: PublishViewModel by activityViewModels{
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish_notes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        binding.btnToPublishPrice.setOnClickListener {
            publishViewModel.setNotes(binding.fieldPublishNotes.editText!!.text.toString())
            val action = PublishNotesFragmentDirections.toPublishPrice()
            navController.navigate(action)
        }
    }
}