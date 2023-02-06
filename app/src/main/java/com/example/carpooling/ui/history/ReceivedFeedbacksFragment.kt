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
import com.example.carpooling.MainNavGraphDirections
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentReceivedFeedbackBinding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class ReceivedFeedbacksFragment : Fragment() {
    private lateinit var binding: FragmentReceivedFeedbackBinding
    private val args: ReceivedFeedbacksFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_received_feedback, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        val adapter =
            FeedbacksAdapter(reviewer = false, onClick = { userId ->
                val action = MainNavGraphDirections.toProfile(userId)
                navController.navigate(action)
            })

        binding.recyclerviewReceivedFeedback.adapter = adapter

        myRidesViewModel.oldRideReceivedFeedbacks(args.rideId).observe(viewLifecycleOwner) { feedbacks ->
            adapter.submitList(feedbacks)
        }
    }
}