package com.example.carpooling.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.data.model.Locations
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.data.restful.requests.ActiveRidesRequest
import com.example.carpooling.databinding.FragmentSearchBinding
import com.example.carpooling.ui.login.LoginFragment
import com.example.carpooling.ui.login.StartFragmentDirections
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.utils.SessionManager
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ActiveRidesViewModel
import com.example.carpooling.viewmodels.DateTimeViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val activeRidesViewModel: ActiveRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    private val dateTimeViewModel: DateTimeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = activeRidesViewModel
        }

        val navController = findNavController()
        val sessionManager = SessionManager(requireContext())
        val authToken = sessionManager.getAuthToken()

        if (authToken == null) {
            if (userViewModel.user.value == null) {
                val action = SearchFragmentDirections.notLogged()
                navController.navigate(action)
            }
        } else {
            userViewModel.initUser(authToken)
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("heya", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                userViewModel.sendPushToken(token)

                Log.d("heyaToken", token)
            })
        }

        val textViewDate = binding.textViewDate
        val textViewTime = binding.textViewTime
        val editTextFrom = binding.editTextFrom
        val editTextTo = binding.editTextTo
        val checkBoxSmoking = binding.checkBoxSmoking
        val checkBoxLuggage = binding.checkBoxLuggage
        val checkBoxSilent = binding.checkBoxSilent
        val btnSearch = binding.btnSearch
        val btnDate = binding.btnDate
        val btnTime = binding.btnTime

        dateTimeViewModel.date.observe(viewLifecycleOwner) { date ->
            textViewDate.text = date
        }

        dateTimeViewModel.time.observe(viewLifecycleOwner) { time ->
            textViewTime.text = time
        }

        btnDate.setOnClickListener {
            navController.navigate(R.id.datePickerFragment)
        }

        btnTime.setOnClickListener {
            navController.navigate(R.id.timePickerFragment)
        }

        btnSearch.setOnClickListener {
            val activeRidesRequest: ActiveRidesRequest = getActiveRidesRequest(
                textViewDate.text.toString(),
                textViewTime.text.toString(),
                editTextFrom.text.toString(),
                editTextTo.text.toString(),
                checkBoxSmoking.isChecked,
                checkBoxLuggage.isChecked,
                checkBoxSilent.isChecked
            )
            activeRidesViewModel.setQuery(activeRidesRequest)
            activeRidesViewModel.executeQuery()
            val action = SearchFragmentDirections.goToSearchResults()
            navController.navigate(action)
        }
    }

    private fun getActiveRidesRequest(
        date: String,
        time: String,
        from: String,
        to: String,
        smoking: Boolean,
        luggage: Boolean,
        silent: Boolean
    ): ActiveRidesRequest {
        val locations: Locations = convertLocations(from, to)
        return ActiveRidesRequest(
            locations.fromLat,
            locations.fromLng,
            locations.toLat,
            locations.toLng,
            date,
            time,
            smoking,
            luggage,
            silent
        )
    }

    private fun convertLocations(from: String, to: String): Locations {
        return Geocoding.getLatLngFromAddresses(binding.root.context, from, to)
    }
}