package com.example.carpooling.data

import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.data.model.OldRide
import com.example.carpooling.data.model.Success
import com.example.carpooling.data.restful.ApiClient

class MyRidesRepository {
    suspend fun getParticipantActiveRides(page: Long) : List<ActiveRide> {
        val apiService = ApiClient.getApiService()
        return apiService.getParticipantActiveRides(page)
    }

    suspend fun getRiderActiveRides(page: Long) : List<ActiveRide> {
        val apiService = ApiClient.getApiService()
        return apiService.getRiderActiveRides(page)
    }

    suspend fun getParticipantOldRides(page: Long) : List<OldRide> {
        val apiService = ApiClient.getApiService()
        return apiService.getParticipantOldRides(page)
    }

    suspend fun getRiderOldRides(page: Long) : List<OldRide> {
        val apiService = ApiClient.getApiService()
        return apiService.getRiderOldRides(page)
    }

    suspend fun getActiveRideByID(id: Long) : ActiveRide {
        val apiService = ApiClient.getApiService()
        return apiService.getActiveRide(id)
    }

    suspend fun getOldRideByID(id: Long) : OldRide {
        val apiService = ApiClient.getApiService()
        return apiService.getOldRide(id)
    }

    suspend fun deleteRide(id: Long) : Success {
        return ApiClient.getApiService().deleteActiveRide(id)
    }

    suspend fun cancelBooking(id: Long) : Success {
        return ApiClient.getApiService().cancelBookingActiveRide(id)
    }
}