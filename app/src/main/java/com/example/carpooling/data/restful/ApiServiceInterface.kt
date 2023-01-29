package com.example.carpooling.data.restful

import com.example.carpooling.data.model.*
import com.example.carpooling.data.restful.requests.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceInterface {

    @POST("api/login")
    suspend fun login(@Body requestBody: LoginRequest): RestResult<LoginResult>

    @GET("api/user")
    suspend fun getUser(): RestResult<User>

    @GET("api/active_ride/{id}")
    suspend fun getActiveRide(@Path("id") id: Long) : RestResult<ActiveRide>

    @GET("api/old_ride/{id}")
    suspend fun getOldRide(@Path("id") id: Long) : RestResult<OldRide>

    @POST("api/active_rides")
    suspend fun getActiveRides(@Body requestBody: ActiveRidesRequest): RestResult<List<ActiveRide>>

    @GET("api/active_rides/{id}/participants")
    suspend fun getActiveRideParticipants(@Path("id") id: Long) : RestResult<List<User>>

    @GET("api/rider/old_rides")
    suspend fun getDriverOldRides() : RestResult<List<OldRide>>

    @GET("api/rider/active_rides")
    suspend fun getDriverActiveRides() : RestResult<List<ActiveRide>>

    @GET("api/participant/active_rides")
    suspend fun getPassengerActiveRides() : RestResult<List<ActiveRide>>

    @GET("api/participant/old_rides")
    suspend fun getPassengerOldRides() : RestResult<List<OldRide>>

    @GET("api/user/feedbacks/received")
    suspend fun getReceivedFeedbacks() : RestResult<List<Feedback>>

    @GET("api/user/feedbacks/sent")
    suspend fun getSentFeedbacks() : RestResult<List<Feedback>>

    @GET("api/old_ride/{id}/feedbacks/received")
    suspend fun getOldRideReceivedFeedbacks(@Path("id") id: Long) : RestResult<List<Feedback>>

    @GET("api/old_ride/{id}/feedbacks/sent")
    suspend fun getOldRideSentFeedbacks(@Path("id") id: Long) : RestResult<List<Feedback>>

    @POST("api/active_ride/publish")
    suspend fun publishActiveRide(@Body requestBody: ActiveRidePublishRequest) : RestResult<Success>

    @GET("api/rider/rating")
    suspend fun getUserRiderRating() : RestResult<RiderRating>

    @GET("api/participant/rating")
    suspend fun getUserPassengerRating() : RestResult<PassengerRating>

    @POST("api/active_ride/{id}/book")
    suspend fun bookActiveRide(@Path("id") id: Long) : RestResult<Success>

    @POST("api/active_ride/{id}/cancel_booking")
    suspend fun cancelBookingActiveRide(@Path("id") id: Long) : RestResult<Success>

    @DELETE("api/active_ride/{id}/delete")
    suspend fun deleteActiveRide(@Path("id") id: Long) : RestResult<Success>

    @POST("api/send/feedback")
    suspend fun sendFeedback(@Body requestBody: SendFeedbackRequest) : RestResult<Success>

    @GET("api/rider/feedbacks/amount")
    suspend fun getNumberOfRiderFeedbacks() : RestResult<Amount>

    @GET("api/participant/feedbacks/amount")
    suspend fun getNumberOfParticipantFeedbacks() : RestResult<Amount>

    @GET("api/rider/old_rides/amount")
    suspend fun getNumberOfRiderOldRides() : RestResult<Amount>

    @GET("api/participant/old_rides/amount")
    suspend fun getNumberOfParticipantOldRides() : RestResult<Amount>

    @PATCH("api/user/update")
    suspend fun updateProfile(@Body requestBody: UpdateProfileRequest) : RestResult<Success>

    @GET("api/old_ride/{id}/feedbacks/missing")
    suspend fun getMissingFeedbackUsers(@Path("id") id: Long) : RestResult<List<User>>

    @POST("api/check/new_cancellations")
    suspend fun getNewCancellations() : RestResult<List<Long>>

    @POST("api/check/new_bookings")
    suspend fun getNewBooking() : RestResult<List<Long>>

    @POST("api/check/new_deleted_rides")
    suspend fun getNewDeletedRides() : RestResult<List<Long>>

    @POST("api/push_token/send")
    suspend fun sendPushToken(@Body requestBody: SendPushTokenRequest) : RestResult<Success>

    @DELETE("api/push_token/delete")
    suspend fun deletePushToken() : RestResult<Success>
}