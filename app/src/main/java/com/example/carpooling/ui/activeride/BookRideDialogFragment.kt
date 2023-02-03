package com.example.carpooling.ui.activeride

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.carpooling.R
import com.example.carpooling.viewmodels.SearchViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BookRideDialogFragment : DialogFragment() {

    private val searchViewModel: SearchViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val args: BookRideDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_book_ride_title)
            .setNegativeButton(resources.getString(R.string.dialog_confirmation_back)) { _, _ ->
            }
            .setPositiveButton(R.string.dialog_book_ride_yes) { _, _ ->
                searchViewModel.bookRide(args.rideId)
            }
            .create()
    }
}