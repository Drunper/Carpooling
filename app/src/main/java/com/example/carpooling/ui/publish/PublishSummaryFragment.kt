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
import androidx.navigation.fragment.findNavController
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish_summary, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = publishViewModel
        }

        val navController = findNavController()
        publishViewModel.publishResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                val action = PublishSummaryFragmentDirections.toPublishSuccess()
                navController.navigate(action)
            }
        }

        binding.btnPublish.setOnClickListener {
            val locations = convertLocations(publishViewModel.from!!, publishViewModel.to!!)
            publishViewModel.publish(locations.fromLat, locations.fromLng, locations.toLat, locations.toLng)
        }
    }

    private fun convertLocations(from: String, to: String) : Locations {
        val geocoder = Geocoder(activity, Locale.getDefault())
        val fromLocation = geocoder.getFromLocationName(from, 1)
        val toLocation = geocoder.getFromLocationName(to, 1)
        val fromLat = fromLocation[0].latitude
        val fromLng = fromLocation[0].longitude

        val toLat = toLocation[0].latitude
        val toLng = toLocation[0].longitude

        return Locations(fromLat, fromLng, toLat, toLng)
    }
}