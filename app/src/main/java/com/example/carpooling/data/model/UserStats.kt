package com.example.carpooling.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserStats(
    @SerializedName("passenger_ride_count")
    val passengerRideCount: Int,
    @SerializedName("driver_ride_count")
    val driverRideCount: Int,
    @SerializedName("passenger_feedback_count")
    val passengerFeedbackCount: Int,
    @SerializedName("driver_feedback_count")
    val driverFeedbackCount: Int,
    @SerializedName("driver_rating")
    val driverRating: Float,
    @SerializedName("passenger_rating")
    val passengerRating: Float
) : Serializable
