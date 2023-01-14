package com.example.carpooling.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class Amount(
    @SerializedName("amount")
    val amount: Int
) : Serializable
