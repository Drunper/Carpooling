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
            return address.getString()
        }

        fun getLatLngFromAddress(context: Context, location: String): Address? {
            val geocoder = Geocoder(context, Locale.getDefault())
            val address = geocoder.getFromLocationName(location, 1)

            return if (address.size >= 1)
                address[0]
            else
                null
        }
    }
}