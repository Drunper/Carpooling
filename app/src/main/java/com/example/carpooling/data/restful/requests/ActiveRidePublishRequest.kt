package com.example.carpooling.data.restful.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    @SerializedName("departure_time")
    val departureTime: String,
    @SerializedName("arrival_time")
    val arrivalTime: String,
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
) : Serializable