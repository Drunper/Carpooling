package com.example.carpooling.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class RiderRating(
    @SerializedName("rider_rating")
    val riderRating : Double
) : Serializable
