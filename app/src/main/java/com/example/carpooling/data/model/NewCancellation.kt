package com.example.carpooling.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class NewCancellation(
    @SerializedName("new_cancellation")
    val newCancellation: Boolean
) : Serializable
