package com.example.carpooling.ui.myrides

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
import com.example.carpooling.databinding.FragmentDriverActiveRideBinding
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class DriverActiveRideFragment : Fragment() {

    private lateinit var binding: FragmentDriverActiveRideBinding
    private val args: DriverActiveRideFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_driver_active_ride, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        myRidesViewModel.getActiveRideByID(args.rideId).observe(viewLifecycleOwner) { ride ->
            val fromAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.from_lat, ride.from_lng)
            val toAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.to_lat, ride.to_lng)

            binding.basicInfo.apply {
                fieldDate.text = ride.date
                fieldTime.text = ride.time
                fieldFrom.text = fromAddressString
                fieldTo.text = toAddressString
                fieldPrice.text = ride.price.toString()
            }

            binding.activeRideInfo.apply {
                fieldNotes.text = ride.addNotes
                checkSmoking.isChecked = ride.smokingAllowed
                checkLuggage.isChecked = ride.luggageAllowed
                checkSilent.isChecked = ride.silentRide
            }

            binding.btnDeleteRide.setOnClickListener{
                val action = DriverActiveRideFragmentDirections.cancelRide(args.rideId)
                navController.navigate(action)
            }
        }
    }
}