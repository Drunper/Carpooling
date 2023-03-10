package com.example.carpooling.data.restful.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ActiveRidesRequest(
    @SerializedName("from_lat")
    val fromLat: Double,
    @SerializedName("from_lng")
    val fromLng: Double,
    @SerializedName("to_lat")
    val toLat: Double,
    @SerializedName("to_lng")
    val toLng: Double,
    @SerializedName("date")
    val date: String,
    @SerializedName("departure_time")
    val time: String,
    @SerializedName("price_lower")
    val priceLower: Double,
    @SerializedName("price_upper")
    val priceUpper: Double,
    @SerializedName("smoking")
    val smokingAllowed: Boolean,
    @SerializedName("luggage")
    val luggageAllowed: Boolean,
    @SerializedName("silent")
    val silentRide: Boolean
) : Serializable