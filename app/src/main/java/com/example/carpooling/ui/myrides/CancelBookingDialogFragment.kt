package com.example.carpooling.ui.myrides

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.carpooling.R
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CancelBookingDialogFragment : DialogFragment() {

    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val args: CancelBookingDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(R.string.dialog_cancel_booking_title)
            .setNegativeButton(resources.getString(R.string.dialog_confirmation_back)) { _, _ ->
            }
            .setPositiveButton(R.string.dialog_cancel_booking_yes) { _, _ ->
                myRidesViewModel.cancelBooking(args.rideId)
            }
            .create()
    }
}