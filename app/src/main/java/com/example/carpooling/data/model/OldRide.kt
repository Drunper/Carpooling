package com.example.carpooling.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OldRide (
    @SerializedName("id")
    val id: Long,
    @SerializedName("from_lat")
    val from_lat: Double,
    @SerializedName("from_lng")
    val from_lng: Double,
    @SerializedName("to_lat")
    val to_lat: Double,
    @SerializedName("to_lng")
    val to_lng: Double,
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("participants")
    val participants: List<User>
) : Serializable