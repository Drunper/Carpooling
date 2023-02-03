package com.example.carpooling.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class DriverRating(
    @SerializedName("driver_rating")
    val driverRating : Float
) : Serializable
