package com.example.carpooling.ui.myrides

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpooling.MainNavGraphDirections
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentCancelActiveRideBinding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class CancelRideFragment : Fragment() {

    private lateinit var binding: FragmentCancelActiveRideBinding
    private val args: CancelRideFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cancel_active_ride, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.btnConfirmDeleteRide.setOnClickListener{
            myRidesViewModel.deleteRide(args.rideID)
        }

        myRidesViewModel.deleteRideResult.observe(viewLifecycleOwner) { success ->
            if(success) {
                val action = MainNavGraphDirections.toSuccessDialog(title = R.string.success_cancel_ride_title, message = R.string.success_cancel_ride_message)
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.myRidesFragment, false).build()
                navController.navigate(action, navOptions)
            }
        }
    }
}