package com.example.carpooling.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.example.carpooling.data.model.Locations
import java.util.*

class Geocoding {
    companion object Functions {
        fun getAddressFromLatLng(context: Context, lat: Double, lng: Double): String {
            val geocoder = Geocoder(context, Locale.getDefault())
            val address =
                geocoder.getFromLocation(lat, lng, 1)[0]
            return "${address.thoroughfare} ${address.subThoroughfare}, ${address.locality}"
        }

        fun getLatLngFromAddresses(context: Context, from: String, to: String): Locations {
            val geocoder = Geocoder(context, Locale.getDefault())
            val fromLocation = geocoder.getFromLocationName(from, 1)
            val toLocation = geocoder.getFromLocationName(to, 1)
            val fromLat = fromLocation[0].latitude
            val fromLng = fromLocation[0].longitude

            val toLat = toLocation[0].latitude
            val toLng = toLocation[0].longitude

            return Locations(fromLat, fromLng, toLat, toLng)
        }

        fun getLatLngFromAddress(context: Context, location: String): Address {
            val geocoder = Geocoder(context, Locale.getDefault())
            val address = geocoder.getFromLocationName(location, 1)

            return address[0]
        }
    }
}