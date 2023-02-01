package com.example.carpooling.ui.myrides

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpooling.MainNavGraphDirections
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentCancelBookingBinding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class CancelBookingFragment : Fragment() {

    private lateinit var binding: FragmentCancelBookingBinding
    private val args: CancelBookingFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cancel_booking, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.btnConfirmCancelBooking.setOnClickListener {
            myRidesViewModel.cancelBooking(args.rideID)
        }

        myRidesViewModel.cancelBookingResult.observe(viewLifecycleOwner) { success ->
            if(success) {
                val action = MainNavGraphDirections.toSuccessDialog(title = R.string.success_cancel_booking_title, message = R.string.success_cancel_booking_message)
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.myRidesFragment, false).build()
                navController.navigate(action, navOptions)
            }
        }
    }
}