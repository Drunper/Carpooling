package com.example.carpooling.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentSearchBinding
import com.example.carpooling.utils.SessionManager
import com.example.carpooling.utils.convertDate
import com.example.carpooling.utils.getString
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.SearchViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.messaging.FirebaseMessaging
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val searchViewModel: SearchViewModel by activityViewModels {
        ViewModelFactory()
    }

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
            viewModel = searchViewModel
        }

        val navController = findNavController()
        val sessionManager = SessionManager(requireContext())
        val authToken = sessionManager.getAuthToken()

        if (authToken == null) {
            if (userViewModel.user.value == null) {
                val action = SearchFragmentDirections.notLogged()
                navController.navigate(action)
            } else {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result
                    userViewModel.sendPushToken(token)
                })
            }
        } else {
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

        binding.fieldPrice.setLabelFormatter { value: Float ->
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.minimumFractionDigits = 0
            format.currency =
                Currency.getInstance(sharedPreferences.getString("currency", "EUR"))
            format.format(value.toDouble())
        }

        searchViewModel.date.observe(viewLifecycleOwner) { date ->
            binding.fieldDate.text = date.convertDate("dd/MM/yyyy", "EEE dd MMM yyyy")
        }

        searchViewModel.time.observe(viewLifecycleOwner) { time ->
            binding.fieldTime.text = time
        }

        searchViewModel.from.observe(viewLifecycleOwner) { from ->
            binding.fieldFrom.text = from.getString()
        }

        searchViewModel.to.observe(viewLifecycleOwner) { to ->
            binding.fieldTo.text = to.getString()
        }

        binding.fieldDate.setOnClickListener {
            val picker = MaterialDatePicker.Builder
                .datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            picker.addOnPositiveButtonClickListener { selection ->
                val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(selection), TimeZone.getDefault().toZoneId())
                val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                searchViewModel.setDate(date.format(dtf))
            }

            picker.show(parentFragmentManager, "datepicker")
        }

        binding.fieldTime.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .build()

            picker.addOnPositiveButtonClickListener {
                val time = LocalTime.of(picker.hour, picker.minute)
                val dtf = DateTimeFormatter.ofPattern("HH:mm")
                searchViewModel.setTime(time.format(dtf))
            }
            picker.show(parentFragmentManager, "timepicker")
        }

        binding.fieldFrom.setOnClickListener {
            val action = SearchFragmentDirections.insertLocation(from = true)
            navController.navigate(action)
        }

        binding.fieldTo.setOnClickListener {
            val action = SearchFragmentDirections.insertLocation(from = false)
            navController.navigate(action)
        }

        binding.btnSearch.setOnClickListener {
            searchViewModel.setQuery(
                priceLower = binding.fieldPrice.values[0].toDouble(),
                priceUpper = binding.fieldPrice.values[1].toDouble(),
                smoking = binding.boxSmoking.isChecked,
                luggage = binding.boxLuggage.isChecked,
                silence = binding.boxSilence.isChecked
            )
            val action = SearchFragmentDirections.goToSearchResults()
            navController.navigate(action)
        }
    }
}