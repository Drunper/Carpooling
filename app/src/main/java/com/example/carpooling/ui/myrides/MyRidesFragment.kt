package com.example.carpooling.ui.myrides

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentMyRidesBinding
import com.example.carpooling.utils.SessionManager
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.messaging.FirebaseMessaging

class MyRidesFragment : Fragment() {
    private lateinit var binding: FragmentMyRidesBinding
    private lateinit var passengerActiveRidesAdapter: MyPassengerActiveRidesAdapter
    private lateinit var driverActiveRidesAdapter: MyDriverActiveRidesAdapter

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_rides, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val tabLayout = binding.tabLayoutMyRides
        val viewPager = binding.viewPagerMyRides

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

        myRidesViewModel.resetDeleteRideResult()
        myRidesViewModel.resetCancelBookingResult()

        passengerActiveRidesAdapter = MyPassengerActiveRidesAdapter { rideID ->
            val action = MyRidesFragmentDirections.toPassengerRide(rideID)
            navController.navigate(action)
        }

        driverActiveRidesAdapter = MyDriverActiveRidesAdapter { rideID ->
            val action = MyRidesFragmentDirections.toDriverRide(rideID)
            navController.navigate(action)
        }

        val viewPagerAdapter = MyRidesTabAdapter(
            passengerActiveRidesAdapter, driverActiveRidesAdapter
        )
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            viewPager.setCurrentItem(tab.position, true)
            if (position == 0)
                tab.text = getString(R.string.tab_title_booked)
            if (position == 1)
                tab.text = getString(R.string.tab_title_published)
        }.attach()

        myRidesViewModel.getPassengerActiveRides().observe(viewLifecycleOwner) { rides ->
            passengerActiveRidesAdapter.submitList(rides)
        }

        myRidesViewModel.getDriverActiveRides().observe(viewLifecycleOwner) { rides ->
            driverActiveRidesAdapter.submitList(rides)
        }
    }
}