package com.example.carpooling.ui.history

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.carpooling.R
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SubmitFeedbackDialogFragment : DialogFragment() {

    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_send_feedback_title)
            .setNegativeButton(resources.getString(R.string.dialog_confirmation_back)) { _, _ ->
            }
            .setPositiveButton(R.string.dialog_send_feedback_yes) { _, _ ->
                myRidesViewModel.sendFeedback()
            }
            .create()
    }
}