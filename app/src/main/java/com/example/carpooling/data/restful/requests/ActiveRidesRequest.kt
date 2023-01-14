package com.example.carpooling.data.restful.requests

import com.google.gson.annotations.SerializedName

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
    @SerializedName("time")
    val time: String,
    @SerializedName("smoking")
    val smokingAllowed: Boolean,
    @SerializedName("luggage")
    val luggageAllowed: Boolean,
    @SerializedName("silent")
    val silentRide: Boolean
)