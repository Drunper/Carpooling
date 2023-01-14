package com.example.carpooling.data.restful.requests

import com.google.gson.annotations.SerializedName

data class ActiveRidePublishRequest(
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
    @SerializedName("price")
    val price: Double,
    @SerializedName("available_seats")
    val availableSeats: Int,
    @SerializedName("smoking")
    val smokingAllowed: Boolean,
    @SerializedName("luggage")
    val luggageAllowed: Boolean,
    @SerializedName("silent")
    val silentRide: Boolean,
    @SerializedName("add_notes")
    val addNotes: String
)