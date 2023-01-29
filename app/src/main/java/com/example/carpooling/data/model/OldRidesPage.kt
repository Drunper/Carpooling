package com.example.carpooling.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OldRidesPage(
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("current_page")
    val currentPage: Long,
    @SerializedName("old_rides")
    val oldRides: List<OldRide>
) : Serializable
