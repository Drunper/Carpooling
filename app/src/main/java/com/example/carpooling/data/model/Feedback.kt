package com.example.carpooling.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class Feedback (
    @SerializedName("id")
    val id: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("recipient")
    val recipient: User,
    @SerializedName("reviewer")
    val reviewer: User,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("text")
    val text: String
) : Serializable