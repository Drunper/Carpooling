package com.example.carpooling.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentSentFeedbacksBinding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class SentFeedbacksFragment : Fragment() {

    private lateinit var binding: FragmentSentFeedbacksBinding
    private val args: SentFeedbacksFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sent_feedbacks, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val adapter =
            FeedbacksAdapter { feedbackId ->

            }

        binding.recyclerViewSentFeedbacks.adapter = adapter

        binding.btnAddFeedback.setOnClickListener {
            val action = SentFeedbacksFragmentDirections.toWriteFeedback(args.rideId)
            navController.navigate(action)
        }

        myRidesViewModel.oldRideSentFeedbacks(args.rideId).observe(viewLifecycleOwner) { feedbacks ->
            adapter.submitList(feedbacks)
        }
    }
}