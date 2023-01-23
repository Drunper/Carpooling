package com.example.carpooling.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResult(
    @SerializedName("auth_token")
    val token: String,
    @SerializedName("user")
    val user: User?
) : Serializable
