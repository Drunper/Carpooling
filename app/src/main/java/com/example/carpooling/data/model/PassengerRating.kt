package com.example.carpooling.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PassengerRating(
    @SerializedName("passenger_rating")
    val passengerRating : Float
) : Serializable
