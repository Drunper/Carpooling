package com.example.carpooling.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PassengerRating(
    @SerializedName("participant_rating")
    val passengerRating : Double
) : Serializable
