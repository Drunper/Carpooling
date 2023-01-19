package com.example.carpooling.data.restful.requests

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class SendFeedbackRequest(
    @SerializedName("ride_id")
    val rideId: Long,
    @SerializedName("recipient_id")
    val recipientId: Long,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("text")
    val text: String
) : Serializable
