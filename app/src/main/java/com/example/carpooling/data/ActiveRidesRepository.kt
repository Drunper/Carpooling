package com.example.carpooling.data

import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.data.model.Success
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.data.restful.requests.ActiveRidePublishRequest
import com.example.carpooling.data.restful.requests.ActiveRidesRequest

class ActiveRidesRepository {
    suspend fun query(request: ActiveRidesRequest, page: Long) : List<ActiveRide> {
        val apiService = ApiClient.getApiService()
        return apiService?.getActiveRides(request, page) ?: listOf()
    }

    suspend fun publish(request: ActiveRidePublishRequest) : Success? {
        val apiService = ApiClient.getApiService()
        return apiService?.publishActiveRide(request)
    }
}