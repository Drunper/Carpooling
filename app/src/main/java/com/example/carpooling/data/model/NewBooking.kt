package com.example.carpooling.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class NewBooking(
    @SerializedName("new_booking")
    val newBooking: Boolean
) : Serializable
