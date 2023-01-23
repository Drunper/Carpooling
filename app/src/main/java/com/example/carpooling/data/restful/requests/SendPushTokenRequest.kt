package com.example.carpooling.data.restful.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SendPushTokenRequest(
    @SerializedName("push_token")
    val pushToken: String
) : Serializable
