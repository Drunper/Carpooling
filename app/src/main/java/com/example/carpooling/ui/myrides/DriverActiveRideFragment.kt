package com.example.carpooling.ui.myrides

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.carpooling.MainNavGraphDirections
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentDriverActiveRideBinding
import com.example.carpooling.ui.activeride.PassengerAdapter
import com.example.carpooling.utils.*
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import java.text.NumberFormat
import java.util.*

class DriverActiveRideFragment : Fragment() {

    private lateinit var binding: FragmentDriverActiveRideBinding
    private val args: DriverActiveRideFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_driver_active_ride,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        val sessionManager = SessionManager(requireContext())
        val authToken = sessionManager.getAuthToken()

        if (authToken != null) {
            userViewModel.initUser(authToken)
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                userViewModel.sendPushToken(token)

            })
        }

        myRidesViewModel.getActiveRideByID(args.rideId).observe(viewLifecycleOwner) { ride ->
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
                fieldRideDriverName.text = getString(R.string.you)
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

            myRidesViewModel.deleteRideResult.observe(viewLifecycleOwner) { success ->
                if (success == true) {
                    binding.root.showSnackbar(
                        requireView(),
                        getString(R.string.snackbar_cancel_ride_success),
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

            binding.btnDeleteRide.setOnClickListener {
                val action = DriverActiveRideFragmentDirections.toCancelRideDialog(args.rideId)
                navController.navigate(action)
            }
        }
    }
}