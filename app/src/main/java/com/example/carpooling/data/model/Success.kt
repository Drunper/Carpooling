package com.example.carpooling.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class Success(
    @SerializedName("success")
    val success: Boolean
) : Serializable
