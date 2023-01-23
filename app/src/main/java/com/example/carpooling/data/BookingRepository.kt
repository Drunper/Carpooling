package com.example.carpooling.data

import com.example.carpooling.data.model.Success
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.data.restful.RestResult

class BookingRepository {
    suspend fun bookRide(id: Long) : RestResult<Success> {
        return ApiClient.getApiService().bookActiveRide(id)
    }

    suspend fun cancelBooking(id: Long) : RestResult<Success> {
        return ApiClient.getApiService().cancelBookingActiveRide(id)
    }
}