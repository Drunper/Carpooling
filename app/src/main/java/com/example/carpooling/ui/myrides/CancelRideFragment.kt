package com.example.carpooling.ui.myrides

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
import com.example.carpooling.databinding.FragmentCancelRideBinding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class CancelRideFragment : Fragment() {

    private lateinit var binding: FragmentCancelRideBinding
    private val args: CancelRideFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cancel_ride, container, false)
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
                navController.popBackStack(R.id.myRidesFragment, false)
            }
        }
    }
}