package com.example.carpooling.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentPassengerOldRideBinding
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class PassengerOldRideFragment : Fragment() {
    private lateinit var binding: FragmentPassengerOldRideBinding
    private val args: PassengerOldRideFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_passenger_old_ride, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        myRidesViewModel.getOldRideByID(args.rideId).observe(viewLifecycleOwner) { ride ->
            val fromAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.from_lat, ride.from_lng)
            val toAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.to_lat, ride.to_lng)

            binding.basicInfo.apply {
                fieldDate.text = ride.date
                fieldTime.text = ride.time
                fieldFrom.text = fromAddressString
                fieldTo.text = toAddressString
                fieldPrice.text = ride.price.toString()
            }

            binding.btnPReceivedFeedbacks.setOnClickListener {
                val action = PassengerOldRideFragmentDirections.toPassengerFeedbacks(args.rideId)
                navController.navigate(action)
            }

            binding.btnPSentFeedbacks.setOnClickListener {
                val action = PassengerOldRideFragmentDirections.toPassengerSentFeedbacks(args.rideId)
                navController.navigate(action)
            }
        }
    }
}