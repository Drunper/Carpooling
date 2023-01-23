package com.example.carpooling.data

import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.data.model.Success
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.data.restful.RestResult
import com.example.carpooling.data.restful.requests.ActiveRidePublishRequest
import com.example.carpooling.data.restful.requests.ActiveRidesRequest

class ActiveRidesRepository {
    suspend fun query(request: ActiveRidesRequest, page: Long) : RestResult<List<ActiveRide>> {
        return ApiClient.getApiService().getActiveRides(request, page)
    }

    suspend fun publish(request: ActiveRidePublishRequest) : RestResult<Success> {
        return ApiClient.getApiService().publishActiveRide(request)
    }
}