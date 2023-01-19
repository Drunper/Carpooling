package com.example.carpooling.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpooling.R
import com.example.carpooling.data.model.User
import com.example.carpooling.data.restful.requests.SendFeedbackRequest
import com.example.carpooling.databinding.FragmentWriteFeedbackBinding
import com.example.carpooling.viewmodels.MyRidesViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class WriteFeedbackFragment : Fragment() {

    private lateinit var binding: FragmentWriteFeedbackBinding
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
        val ratingPicker = binding.fieldRating
        ratingPicker.minValue = 1
        ratingPicker.maxValue = 5
        ratingPicker.displayedValues = arrayOf("1", "2", "3", "4", "5")

        val spinner = binding.fieldFeedbackRecipient

        myRidesViewModel.getMissingUserFeedbacks(args.rideId).observe(viewLifecycleOwner) { users ->
            val items = users.map { user -> SpinnerItem(id = user.id, username = user.username) }
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.apply {
                adapter = spinnerAdapter
                setSelection(0, false)
                prompt = "Seleziona l'utente che vuoi recensire"
            }

            binding.btnSendFeedback.setOnClickListener {
                val request = SendFeedbackRequest(
                    rideId = args.rideId,
                    rating = ratingPicker.value,
                    text = binding.fieldFeedbackText.text.toString(),
                    recipientId = (spinner.selectedItem as SpinnerItem).id
                )

                myRidesViewModel.sendFeedback(request).observe(viewLifecycleOwner) { success ->
                    if (success) {
                        val action = WriteFeedbackFragmentDirections.toWriteFeedbackSuccess()
                        navController.navigate(action)
                    }
                }
            }
        }
    }
}