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

    suspend fun getActiveRide(id: Long) : RestResult<ActiveRide> {
        return ApiClient.getApiService().getActiveRide(id)
    }

    suspend fun bookRide(id: Long) : RestResult<Success> {
        return ApiClient.getApiService().bookActiveRide(id)
    }

    // My rides

    suspend fun getPassengerActiveRides() : RestResult<List<ActiveRide>> {
        return ApiClient.getApiService().getPassengerActiveRides()
    }

    suspend fun getDriverActiveRides() : RestResult<List<ActiveRide>> {
        return ApiClient.getApiService().getDriverActiveRides()
    }

    suspend fun getActiveRideByID(id: Long) : RestResult<ActiveRide> {
        return ApiClient.getApiService().getActiveRide(id)
    }

    suspend fun deleteRide(id: Long) : RestResult<Success> {
        return ApiClient.getApiService().deleteActiveRide(id)
    }

    suspend fun cancelBooking(id: Long) : RestResult<Success> {
        return ApiClient.getApiService().cancelBookingActiveRide(id)
    }

    // History, Feedbacks

    suspend fun getPassengerOldRides() : RestResult<List<OldRide>> {
        return ApiClient.getApiService().getPassengerOldRides()
    }

    suspend fun getDriverOldRides() : RestResult<List<OldRide>> {
        return ApiClient.getApiService().getDriverOldRides()
    }

    suspend fun getOldRideByID(id: Long) : RestResult<OldRide> {
        return ApiClient.getApiService().getOldRide(id)
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

    suspend fun getDriverRating(): RestResult<RiderRating> {
        return ApiClient.getApiService().getUserRiderRating()
    }

    suspend fun getPassengerRating(): RestResult<PassengerRating> {
        return ApiClient.getApiService().getUserPassengerRating()
    }

    suspend fun updateProfile(request: UpdateProfileRequest): RestResult<Success> {
        return ApiClient.getApiService().updateProfile(request)
    }
}