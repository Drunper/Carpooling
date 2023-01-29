package com.example.carpooling.ui.search

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentLocationBinding
import com.example.carpooling.utils.Geocoding
import com.example.carpooling.utils.showSnackbar
import com.example.carpooling.viewmodels.SearchViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar

class LocationFragment : Fragment() {
    private lateinit var binding: FragmentLocationBinding
    private lateinit var locationClient: FusedLocationProviderClient
    private val args:LocationFragmentArgs by navArgs()
    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissions.entries.forEach {
                Log.d("permissionsDebug", "${it.key} = ${it.value}")
            }
        }

    private var currentLocation: Location? = null
    private var currentLocationString: String? = null

    private val searchViewModel: SearchViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location, container, false)
        locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        binding.btnCurrentPosition.setOnClickListener {
            getLocation()
            if (currentLocationString != null) {
            }
        }

        if (args.from)
            binding.fieldLocation.hint = getString(R.string.from)
        else
            binding.fieldLocation.hint = getString(R.string.to)

        binding.btnInsert.setOnClickListener {
            val location = Geocoding.getLatLngFromAddress(
                requireContext(),
                binding.fieldLocation.editText!!.text.toString()
            )
            if (args.from)
                searchViewModel.setFrom(location)
            else
                searchViewModel.setTo(location)
            navController.popBackStack()
        }
    }

    private fun getLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                locationClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        currentLocation = location
                        currentLocationString = Geocoding.getAddressFromLatLng(requireContext(), location.latitude, location.longitude)
                        binding.fieldLocation.editText!!.setText(currentLocationString)
                    }
                    else {
                        Log.d("locationDebug","permissions granted, location is null")
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestLocationPermissions()
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestLocationPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            binding.root.showSnackbar(
                requireView(),
                requireContext().getString(R.string.location_permission_required),
                Snackbar.LENGTH_INDEFINITE,
                requireContext().getString(R.string.ok),
            ) {
                launchMultiplePermissions()
            }
        } else {
            launchMultiplePermissions()
        }
    }

    private fun launchMultiplePermissions() {
        requestMultiplePermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
}