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
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentActiveRideBinding
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.viewmodels.SearchViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
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
        val fragmentBinding: FragmentActiveRideBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_active_ride, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        searchViewModel.getActiveRideById(args.activeRideId).observe(viewLifecycleOwner) { ride ->
            ride?.let {
                val fromAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.from_lat, ride.from_lng)
                val toAddressString = Geocoding.getAddressFromLatLng(binding.root.context, ride.to_lat, ride.to_lng)

                binding.basicInfo.apply {
                    fieldDate.text = ride.date
                    fieldTime.text = ride.departureTime
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

                val btnBook = binding.btnActiveRideBook
                btnBook.setOnClickListener{
                    val action = ActiveRideFragmentDirections.bookRide(args.activeRideId)
                    navController.navigate(action)
                }
            }
        }
    }
}