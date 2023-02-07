package com.example.carpooling.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpooling.MainNavGraphDirections
import com.example.carpooling.R
import com.example.carpooling.data.model.User
import com.example.carpooling.data.restful.requests.SendFeedbackRequest
import com.example.carpooling.databinding.FragmentWriteFeedbackBinding
import com.example.carpooling.utils.showSnackbar
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class WriteFeedbackFragment : Fragment() {

    private lateinit var binding: FragmentWriteFeedbackBinding
    private var recipientId: Int = -1
    private val args: WriteFeedbackFragmentArgs by navArgs()
    private val myRidesViewModel: MyRidesViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_feedback, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        myRidesViewModel.getMissingUserFeedbacks(args.rideId).observe(viewLifecycleOwner) { users ->
            val usersIds = users.map { user -> user.id }
            val options = users.map { user -> user.username}
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, options)
            (binding.fieldWriteFeedbackRecipient.editText as? AutoCompleteTextView)?.setAdapter(adapter)

            (binding.fieldWriteFeedbackRecipient.editText as? MaterialAutoCompleteTextView)?.setOnItemClickListener { _, _, position, _ ->
                recipientId = usersIds[position]
                binding.btnSendFeedback.isEnabled = true
            }

            binding.btnSendFeedback.setOnClickListener {
                val rating = binding.writeFeedbackRating.rating
                if (rating < 1) {
                    binding.root.showSnackbar(
                        requireView(),
                        getString(R.string.error_necessary_rating),
                        Snackbar.LENGTH_INDEFINITE,
                        requireContext().getString(R.string.snackbar_ok),
                    ) {}
                } else {
                    myRidesViewModel.feedbackRequest = SendFeedbackRequest(
                        rideId = args.rideId,
                        rating = binding.writeFeedbackRating.rating.toInt(),
                        text = binding.fieldWriteFeedbackText.editText?.text.toString(),
                        recipientId = recipientId
                    )

                    val action = WriteFeedbackFragmentDirections.toSubmitFeedbackDialogFragment()
                    navController.navigate(action)
                }
            }
        }

        myRidesViewModel.sendFeedbackResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                binding.root.showSnackbar(
                    requireView(),
                    getString(R.string.snackbar_send_feedback_success),
                    Snackbar.LENGTH_LONG,
                    null
                ) {}
                navController.popBackStack()
            } else if (success == false) {
                binding.root.showSnackbar(
                    requireView(),
                    getString(R.string.snackbar_processing_request_error),
                    Snackbar.LENGTH_LONG,
                    null
                ) {}
            }
        }
    }
}