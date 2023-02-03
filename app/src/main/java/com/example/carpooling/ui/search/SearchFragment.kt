package com.example.carpooling.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentSearchBinding
import com.example.carpooling.utils.SessionManager
import com.example.carpooling.utils.formatCurrency
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.SearchViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.text.NumberFormat
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

        binding.scrollview

        binding.fieldPrice.setLabelFormatter { value: Float ->
            value.toDouble().formatCurrency(requireContext())
        }

        searchViewModel.date.observe(viewLifecycleOwner) { date ->
            binding.fieldDate.text = date
        }

        searchViewModel.time.observe(viewLifecycleOwner) { time ->
            binding.fieldTime.text = time
        }

        searchViewModel.from.observe(viewLifecycleOwner) { from ->
            val string = "${from.thoroughfare} ${from.subThoroughfare}, ${from.locality}"
            binding.fieldFrom.text = string
        }

        searchViewModel.to.observe(viewLifecycleOwner) { to ->
            val string = "${to.thoroughfare} ${to.subThoroughfare}, ${to.locality}"
            binding.fieldTo.text = string
        }

        binding.fieldDate.setOnClickListener {
            navController.navigate(R.id.datePickerFragment)
        }

        binding.fieldTime.setOnClickListener {
            navController.navigate(R.id.timePickerFragment)
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