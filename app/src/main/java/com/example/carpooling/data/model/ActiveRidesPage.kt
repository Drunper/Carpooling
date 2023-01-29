package com.example.carpooling.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ActiveRidesPage(
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("current_page")
    val currentPage: Long,
    @SerializedName("active_rides")
    val activeRides: List<ActiveRide>
) : Serializable