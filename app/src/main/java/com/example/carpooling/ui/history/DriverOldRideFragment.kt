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
import com.bumptech.glide.Glide
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentDriverOldRideBinding
import com.example.carpooling.ui.activeride.PassengerAdapter
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import java.text.NumberFormat
import java.util.*

class DriverOldRideFragment : Fragment() {

    private lateinit var binding: FragmentDriverOldRideBinding
    private val args: DriverOldRideFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_driver_old_ride, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        myRidesViewModel.getOldRideByID(args.rideId).observe(viewLifecycleOwner) { ride ->
            val fromAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.from_lat, ride.from_lng)
            val toAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.to_lat, ride.to_lng)

            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 2
            format.minimumFractionDigits = 2
            format.currency =
                Currency.getInstance("EUR") // TODO: bisogna usare la currency utilizzata di default dal sistema

            binding.basicInfo.apply {
                fieldRideDate.text = ride.date
                fieldRideDepartureTime.text = ride.departureTime
                fieldRideArrivalTime.text = ride.arrivalTime
                fieldRideFrom.text = fromAddressString
                fieldRideTo.text = toAddressString
                fieldRidePrice.text = format.format(ride.price)
            }

            binding.ridePassengersInfo.apply {
                fieldRideRiderName.text = getString(R.string.you)
                val picReference = ride.rider.profilePicReference
                Glide.with(requireContext()).load("http://10.0.2.2:8080/carpooling_images/$picReference").into(imageRideRider)
                val adapter = PassengerAdapter()
                passengersRecyclerView.adapter = adapter
                adapter.submitList(ride.passengers)
            }


            binding.btnDriverReceivedFeedbacks.setOnClickListener {
                val action = DriverOldRideFragmentDirections.toDriverFeedbacks(args.rideId)
                navController.navigate(action)
            }

            binding.btnDriverSentFeedbacks.setOnClickListener {
                val action = DriverOldRideFragmentDirections.toDriverSentFeedbacks(args.rideId)
                navController.navigate(action)
            }
        }
    }
}