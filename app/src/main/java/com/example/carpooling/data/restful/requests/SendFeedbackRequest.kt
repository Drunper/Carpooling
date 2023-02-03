package com.example.carpooling.data.restful.requests

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class SendFeedbackRequest(
    @SerializedName("ride_id")
    val rideId: Int,
    @SerializedName("recipient_id")
    val recipientId: Int,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("text")
    val text: String
) : Serializable
