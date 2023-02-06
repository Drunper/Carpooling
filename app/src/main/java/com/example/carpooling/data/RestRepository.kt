package com.example.carpooling.data

import com.example.carpooling.data.model.*
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.data.restful.RestResult
import com.example.carpooling.data.restful.requests.*

class RestRepository {

    // Search, Publish, Booking

    suspend fun query(request: ActiveRidesRequest) : RestResult<List<ActiveRide>> {
        return ApiClient.getApiService().getActiveRides(request)
    }

    suspend fun publish(request: ActiveRidePublishRequest) : RestResult<Success> {
        return ApiClient.getApiService().publishActiveRide(request)
    }

    suspend fun getActiveRide(id: Int) : RestResult<ActiveRide> {
        return ApiClient.getApiService().getActiveRide(id)
    }

    suspend fun bookRide(id: Int) : RestResult<Success> {
        return ApiClient.getApiService().bookActiveRide(id)
    }

    // My rides

    suspend fun getPassengerActiveRides() : RestResult<List<ActiveRide>> {
        return ApiClient.getApiService().getPassengerActiveRides()
    }

    suspend fun getDriverActiveRides() : RestResult<List<ActiveRide>> {
        return ApiClient.getApiService().getDriverActiveRides()
    }

    suspend fun getActiveRideByID(id: Int) : RestResult<ActiveRide> {
        return ApiClient.getApiService().getActiveRide(id)
    }

    suspend fun deleteRide(id: Int) : RestResult<Success> {
        return ApiClient.getApiService().deleteActiveRide(id)
    }

    suspend fun cancelBooking(id: Int) : RestResult<Success> {
        return ApiClient.getApiService().cancelBookingActiveRide(id)
    }

    // History, Feedbacks

    suspend fun getPassengerOldRides() : RestResult<List<OldRide>> {
        return ApiClient.getApiService().getPassengerOldRides()
    }

    suspend fun getDriverOldRides() : RestResult<List<OldRide>> {
        return ApiClient.getApiService().getDriverOldRides()
    }

    suspend fun getOldRideByID(id: Int) : RestResult<OldRide> {
        return ApiClient.getApiService().getOldRide(id)
    }

    suspend fun getOldRideReceivedFeedbacks(id: Int): RestResult<List<Feedback>> {
        return ApiClient.getApiService().getOldRideReceivedFeedbacks(id)
    }

    suspend fun getOldRideSentFeedbacks(id: Int): RestResult<List<Feedback>> {
        return ApiClient.getApiService().getOldRideSentFeedbacks(id)
    }

    suspend fun sendFeedback(request: SendFeedbackRequest): RestResult<Success> {
        return ApiClient.getApiService().sendFeedback(request)
    }

    suspend fun getMissingFeedbackUsers(id: Int) : RestResult<List<User>> {
        return ApiClient.getApiService().getMissingFeedbackUsers(id)
    }

    // Login, Logout, Profile

    fun logout() {
        ApiClient.resetApiService()
    }

    suspend fun login(request: LoginRequest): RestResult<LoginResult> {
        return ApiClient.getLoginApiService().login(request)
    }

    suspend fun getUser(): RestResult<User> {
        return ApiClient.getApiService().getUser()
    }

    suspend fun sendToken(request: SendPushTokenRequest): RestResult<Success> {
        return ApiClient.getApiService().sendPushToken(request)
    }

    suspend fun deleteToken(): RestResult<Success> {
        return ApiClient.getApiService().deletePushToken()
    }

    suspend fun updateProfile(request: UpdateProfileRequest): RestResult<Success> {
        return ApiClient.getApiService().updateProfile(request)
    }

    suspend fun getUserById(id: Int): RestResult<User> {
        return ApiClient.getApiService().getUserById(id)
    }

    suspend fun getUserStats(id: Int): RestResult<UserStats> {
        return ApiClient.getApiService().getUserStats(id)
    }

    suspend fun getDriverReceivedFeedback(id: Int): RestResult<List<Feedback>> {
        return ApiClient.getApiService().getDriverReceivedFeedback(id)
    }

    suspend fun getDriverSentFeedback(id: Int): RestResult<List<Feedback>> {
        return ApiClient.getApiService().getDriverSentFeedback(id)
    }

    suspend fun getPassengerReceivedFeedback(id: Int): RestResult<List<Feedback>> {
        return ApiClient.getApiService().getPassengerReceivedFeedback(id)
    }

    suspend fun getPassengerSentFeedback(id: Int): RestResult<List<Feedback>> {
        return ApiClient.getApiService().getPassengerSentFeedback(id)
    }
}