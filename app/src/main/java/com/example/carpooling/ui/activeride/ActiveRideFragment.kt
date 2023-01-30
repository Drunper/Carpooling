package com.example.carpooling.ui.activeride

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
import com.example.carpooling.databinding.FragmentActiveRideBinding
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.viewmodels.SearchViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import java.text.NumberFormat
import java.util.*

class ActiveRideFragment : Fragment() {

    private lateinit var binding: FragmentActiveRideBinding
    private val args: ActiveRideFragmentArgs by navArgs()

    private val searchViewModel: SearchViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding: FragmentActiveRideBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_active_ride, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        searchViewModel.getActiveRideById(args.activeRideId).observe(viewLifecycleOwner) { ride ->
            val fromAddressString =
                Geocoding.getAddressFromLatLng(binding.root.context, ride.from_lat, ride.from_lng)
            val toAddressString =
                Geocoding.getAddressFromLatLng(binding.root.context, ride.to_lat, ride.to_lng)

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

            binding.activeRideInfo.apply {
                fieldActiveRideNotes.text = ride.notes
                val smoking = if (ride.smokingAllowed) {
                    R.drawable.smoking_image
                } else {
                    R.drawable.no_smoking_image
                }

                val luggage = if (ride.luggageAllowed) {
                    R.drawable.luggage_image
                } else {
                    R.drawable.no_luggage_image
                }

                val silent = if (ride.silentRide) {
                    R.drawable.silent_image
                } else {
                    R.drawable.no_silent_image
                }
                imageActiveRideSmoking.setImageResource(smoking)
                imageActiveRideLuggage.setImageResource(luggage)
                imageActiveRideSilent.setImageResource(silent)
            }

            binding.ridePassengersInfo.apply {
                fieldRideRiderName.text = ride.rider.username
                val picReference = ride.rider.profilePicReference
                Glide.with(requireContext()).load("http://10.0.2.2:8080/carpooling_images/$picReference").into(imageRideRider)
                val adapter = PassengerAdapter()
                passengersRecyclerView.adapter = adapter
                adapter.submitList(ride.passengers)
            }

            val btnBook = binding.btnBook
            btnBook.setOnClickListener {
                val action = ActiveRideFragmentDirections.bookRide(args.activeRideId)
                navController.navigate(action)
            }
        }
    }
}