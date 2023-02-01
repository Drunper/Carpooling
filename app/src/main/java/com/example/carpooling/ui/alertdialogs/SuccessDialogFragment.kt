package com.example.carpooling.ui.alertdialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.carpooling.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SuccessDialogFragment : DialogFragment() {

    private val args: SuccessDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(args.title)
            .setMessage(args.message)
            .setIcon(R.drawable.ic_check_outline)
            .setPositiveButton(R.string.snackbar_ok) { _, _ -> }
            .create()
    }
}