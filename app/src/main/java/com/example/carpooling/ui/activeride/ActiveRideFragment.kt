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
import com.example.carpooling.MainNavGraphDirections
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentActiveRideBinding
import com.example.carpooling.ui.search.SearchResultFragmentDirections
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.utils.convertDate
import com.example.carpooling.utils.formatCurrency
import com.example.carpooling.utils.showSnackbar
import com.example.carpooling.viewmodels.SearchViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
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

            binding.basicInfo.apply {
                fieldRideDate.text = ride.date.convertDate("dd/MM/yyyy", "EEE dd MMM yyyy")
                fieldRideDepartureTime.text = ride.departureTime
                fieldRideArrivalTime.text = ride.arrivalTime
                fieldRideFrom.text = fromAddressString
                fieldRideTo.text = toAddressString
                fieldRidePrice.text = ride.price.formatCurrency(requireContext())
            }

            binding.activeRideInfo.apply {
                fieldAvailableSeats.text = getString(R.string.format_field_available_seats, ride.availableSeats)
                fieldActiveRideNotes.text = ride.notes
                val smoking = if (ride.smokingAllowed) {
                    R.drawable.smoking_icon
                } else {
                    R.drawable.no_smoking_icon
                }

                val luggage = if (ride.luggageAllowed) {
                    R.drawable.luggage_icon
                } else {
                    R.drawable.no_luggage_icon
                }

                val silent = if (ride.silentRide) {
                    R.drawable.silent_icon
                } else {
                    R.drawable.no_silent_icon
                }
                imageActiveRideSmoking.setImageResource(smoking)
                imageActiveRideLuggage.setImageResource(luggage)
                imageActiveRideSilent.setImageResource(silent)
            }

            binding.ridePassengersInfo.apply {
                fieldRideDriverName.text = ride.driver.username
                val picReference = ride.driver.profilePicReference
                Glide.with(requireContext())
                    .load("http://10.0.2.2:8080/carpooling_images/$picReference")
                    .error(R.drawable.ic_user)
                    .into(imageRideDriver)
                val adapter = PassengerAdapter { userID ->
                    val action = MainNavGraphDirections.toProfile(userID)
                    navController.navigate(action)
                }
                passengersRecyclerView.adapter = adapter
                adapter.submitList(ride.passengers)

                layoutDriver.setOnClickListener {
                    val action = MainNavGraphDirections.toProfile(ride.driver.id)
                    navController.navigate(action)
                }
            }

            val btnBook = binding.btnBook
            btnBook.setOnClickListener {
                val action = ActiveRideFragmentDirections.toBookRideDialog(args.activeRideId)
                navController.navigate(action)
            }

            searchViewModel.bookRideResult.observe(viewLifecycleOwner) { success ->
                if (success) {
                    binding.root.showSnackbar(
                        requireView(),
                        getString(R.string.snackbar_booking_success),
                        Snackbar.LENGTH_LONG,
                        null
                    ) {}
                    navController.popBackStack()
                } else if (success == false) {
                    binding.root.showSnackbar(
                        requireView(),
                        getString(R.string.snackbar_processing_request_error),
                        Snackbar.LENGTH_LONG,
                        null
                    ) {}
                }
            }
        }
    }
}