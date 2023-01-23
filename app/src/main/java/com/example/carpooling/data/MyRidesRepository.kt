package com.example.carpooling.data

import com.example.carpooling.data.model.*
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.data.restful.RestResult
import com.example.carpooling.data.restful.requests.SendFeedbackRequest

class MyRidesRepository {
    suspend fun getParticipantActiveRides(page: Long) : RestResult<List<ActiveRide>> {
        return ApiClient.getApiService().getParticipantActiveRides(page)
    }

    suspend fun getRiderActiveRides(page: Long) : RestResult<List<ActiveRide>> {
        return ApiClient.getApiService().getRiderActiveRides(page)
    }

    suspend fun getParticipantOldRides(page: Long) : RestResult<List<OldRide>> {
        return ApiClient.getApiService().getParticipantOldRides(page)
    }

    suspend fun getRiderOldRides(page: Long) : RestResult<List<OldRide>> {
        return ApiClient.getApiService().getRiderOldRides(page)
    }

    suspend fun getActiveRideByID(id: Long) : RestResult<ActiveRide> {
        return ApiClient.getApiService().getActiveRide(id)
    }

    suspend fun getOldRideByID(id: Long) : RestResult<OldRide> {
        return ApiClient.getApiService().getOldRide(id)
    }

    suspend fun deleteRide(id: Long) : RestResult<Success> {
        return ApiClient.getApiService().deleteActiveRide(id)
    }

    suspend fun cancelBooking(id: Long) : RestResult<Success> {
        return ApiClient.getApiService().cancelBookingActiveRide(id)
    }

    suspend fun getOldRideReceivedFeedbacks(id: Long): RestResult<List<Feedback>> {
        return ApiClient.getApiService().getOldRideReceivedFeedbacks(id)
    }

    suspend fun getOldRideSentFeedbacks(id: Long): RestResult<List<Feedback>> {
        return ApiClient.getApiService().getOldRideSentFeedbacks(id)
    }

    suspend fun sendFeedback(request: SendFeedbackRequest): RestResult<Success> {
        return ApiClient.getApiService().sendFeedback(request)
    }

    suspend fun getMissingFeedbackUsers(id: Long) : RestResult<List<User>> {
        return ApiClient.getApiService().getMissingFeedbackUsers(id)
    }
}