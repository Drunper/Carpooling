package com.example.carpooling.ui.publish

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.navGraphViewModels
import com.example.carpooling.R
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PublishSubmitDialogFragment : DialogFragment() {

    private val publishViewModel: PublishViewModel by navGraphViewModels(R.id.publish_nav_graph){
        ViewModelFactory()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_publish_title)
            .setNegativeButton(resources.getString(R.string.dialog_confirmation_back)) { _, _ ->
            }
            .setPositiveButton(R.string.dialog_publish_yes) { _, _ ->
                publishViewModel.publish()
            }
            .create()
    }
}