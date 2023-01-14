package com.example.carpooling.ui.myrides

import android.location.Geocoder
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
import com.example.carpooling.databinding.FragmentParticipantRideBinding
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import java.util.*

class ParticipantRideFragment : Fragment() {

    private lateinit var binding: FragmentParticipantRideBinding
    private val args: ParticipantRideFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_participant_ride, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.lifecycleOwner = viewLifecycleOwner
        myRidesViewModel.getActiveRideByID(args.rideId).observe(viewLifecycleOwner) { ride ->
            /*val geocoder = Geocoder(binding.root.context, Locale.getDefault())
            val fromAddress =
                geocoder.getFromLocation(ride.from_lat, ride.from_lng, 1)[0]
            val fromAddressString = "${fromAddress.locality}, ${fromAddress.adminArea}"
            val toAddress =
                geocoder.getFromLocation(ride.to_lat, ride.to_lng, 1)[0]
            val toAddressString = "${toAddress.locality}, ${toAddress.adminArea}"*/

            val fromAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.from_lat, ride.from_lng)
            val toAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.to_lat, ride.to_lng)

            binding.apply {
                activeRide = ride
            }

            binding.btnCancelBooking.setOnClickListener{
                val action = ParticipantRideFragmentDirections.cancelBooking(args.rideId)
                navController.navigate(action)
            }
        }
    }

    fun getAddress(lat: Double, lng: Double): String {
        return Geocoding.getAddressFromLatLng(binding.root.context, lat, lng)
    }

}