package com.example.carpooling.ui.publish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentPublishSummaryBinding
import com.example.carpooling.utils.convertDate
import com.example.carpooling.utils.formatCurrency
import com.example.carpooling.utils.getString
import com.example.carpooling.utils.showSnackbar
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class PublishSummaryFragment : Fragment() {

    private lateinit var binding: FragmentPublishSummaryBinding
    private val publishViewModel: PublishViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_publish_summary, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        publishViewModel.from.observe(viewLifecycleOwner) { from ->
            binding.fieldRideTo.text = from.getString()
        }

        publishViewModel.to.observe(viewLifecycleOwner) { to ->
            binding.fieldRideTo.text = to.getString()
        }

        publishViewModel.price.observe(viewLifecycleOwner) { price ->
            if (price != null) {
                binding.fieldRidePrice.text = price.formatCurrency(requireContext())
            } else {
                binding.fieldRidePrice.text = ""
            }
        }

        publishViewModel.date.observe(viewLifecycleOwner) { date ->
            binding.fieldRideDate.text = date.convertDate("dd/MM/yyyy", "EEE dd MMM yyyy")
        }

        publishViewModel.publishResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                binding.root.showSnackbar(
                    requireView(),
                    getString(R.string.snackbar_publish_success),
                    Snackbar.LENGTH_LONG,
                    null
                ) {}
                navController.popBackStack(R.id.publishFragment, true)
            }
        }

        binding.apply {
            viewModel = publishViewModel
            lifecycleOwner = viewLifecycleOwner
            btnPublish.setOnClickListener {
                val action = PublishSummaryFragmentDirections.toPublishSubmitDialog()
                navController.navigate(action)
            }
        }
    }
}