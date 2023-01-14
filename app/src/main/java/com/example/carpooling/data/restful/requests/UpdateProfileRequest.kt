package com.example.carpooling.data.restful.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdateProfileRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("bio")
    val bio: String,
) : Serializable
