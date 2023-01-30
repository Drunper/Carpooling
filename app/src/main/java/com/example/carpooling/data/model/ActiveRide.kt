package com.example.carpooling.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ActiveRide(
    @SerializedName("id")
    val id: Long,
    @SerializedName("from_lat")
    val from_lat: Double,
    @SerializedName("from_lng")
    val from_lng: Double,
    @SerializedName("to_lat")
    val to_lat: Double,
    @SerializedName("to_lng")
    val to_lng: Double,
    @SerializedName("date")
    val date: String,
    @SerializedName("departure_time")
    val departureTime: String,
    @SerializedName("arrival_time")
    val arrivalTime: String,
    @SerializedName("smoking")
    val smokingAllowed: Boolean,
    @SerializedName("luggage")
    val luggageAllowed: Boolean,
    @SerializedName("silent")
    val silentRide: Boolean,
    @SerializedName("available_seats")
    val availableSeats: Int,
    @SerializedName("add_notes")
    val notes: String,
    @SerializedName("rider")
    val rider: User,
    @SerializedName("price")
    val price: Double,
    @SerializedName("participants")
    val passengers: List<User>
) : Serializable