package com.example.carpooling.ui.publish

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.carpooling.MainNavGraphDirections
import com.example.carpooling.R
import com.example.carpooling.data.model.Locations
import com.example.carpooling.data.restful.requests.ActiveRidePublishRequest
import com.example.carpooling.databinding.FragmentPublishSummaryBinding
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
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
        publishViewModel.apply {
            publishResult.observe(viewLifecycleOwner) { success ->
                if (success) {
                    val action = MainNavGraphDirections.toSuccessDialog(title = R.string.success_cancel_ride_title, message = R.string.success_cancel_ride_message)
                    val navOptions = NavOptions.Builder().setPopUpTo(R.id.publishFragment, true).build()
                    navController.navigate(action, navOptions)
                }
            }
        }

        binding.btnPublish.setOnClickListener {
            publishViewModel.publish()
        }
    }
}