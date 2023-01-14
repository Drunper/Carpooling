package com.example.carpooling.data

import com.example.carpooling.data.model.Success
import com.example.carpooling.data.restful.ApiClient

class BookingRepository {
    suspend fun bookRide(id: Long) : Success? {
        return ApiClient.getApiService().bookActiveRide(id)
    }

    suspend fun cancelBooking(id: Long) : Success? {
        return ApiClient.getApiService().cancelBookingActiveRide(id)
    }
}