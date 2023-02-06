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
    suspend fun getActiveRide(@Path("id") id: Int) : RestResult<ActiveRide>

    @GET("api/old_ride/{id}")
    suspend fun getOldRide(@Path("id") id: Int) : RestResult<OldRide>

    @POST("api/active_rides")
    suspend fun getActiveRides(@Body requestBody: ActiveRidesRequest): RestResult<List<ActiveRide>>

    @GET("api/driver/old_rides")
    suspend fun getDriverOldRides() : RestResult<List<OldRide>>

    @GET("api/driver/active_rides")
    suspend fun getDriverActiveRides() : RestResult<List<ActiveRide>>

    @GET("api/passenger/active_rides")
    suspend fun getPassengerActiveRides() : RestResult<List<ActiveRide>>

    @GET("api/passenger/old_rides")
    suspend fun getPassengerOldRides() : RestResult<List<OldRide>>

    @GET("api/old_ride/{id}/feedbacks/received")
    suspend fun getOldRideReceivedFeedbacks(@Path("id") id: Int) : RestResult<List<Feedback>>

    @GET("api/old_ride/{id}/feedbacks/sent")
    suspend fun getOldRideSentFeedbacks(@Path("id") id: Int) : RestResult<List<Feedback>>

    @POST("api/active_ride/publish")
    suspend fun publishActiveRide(@Body requestBody: ActiveRidePublishRequest) : RestResult<Success>

    @POST("api/active_ride/{id}/book")
    suspend fun bookActiveRide(@Path("id") id: Int) : RestResult<Success>

    @POST("api/active_ride/{id}/cancel_booking")
    suspend fun cancelBookingActiveRide(@Path("id") id: Int) : RestResult<Success>

    @DELETE("api/active_ride/{id}/delete")
    suspend fun deleteActiveRide(@Path("id") id: Int) : RestResult<Success>

    @POST("api/send/feedback")
    suspend fun sendFeedback(@Body requestBody: SendFeedbackRequest) : RestResult<Success>

    @PATCH("api/user/update")
    suspend fun updateProfile(@Body requestBody: UpdateProfileRequest) : RestResult<Success>

    @GET("api/old_ride/{id}/feedbacks/missing")
    suspend fun getMissingFeedbackUsers(@Path("id") id: Int) : RestResult<List<User>>

    @POST("api/push_token/send")
    suspend fun sendPushToken(@Body requestBody: SendPushTokenRequest) : RestResult<Success>

    @DELETE("api/push_token/delete")
    suspend fun deletePushToken() : RestResult<Success>

    @GET("api/user/{id}")
    suspend fun getUserById(@Path("id") id: Int) : RestResult<User>

    @GET("api/user/{id}/stats")
    suspend fun getUserStats(@Path("id") id: Int) : RestResult<UserStats>

    @GET("api/driver/{id}/feedback/received")
    suspend fun getDriverReceivedFeedback(@Path("id") id: Int) : RestResult<List<Feedback>>

    @GET("api/driver/{id}/feedback/sent")
    suspend fun getDriverSentFeedback(@Path("id") id: Int) : RestResult<List<Feedback>>

    @GET("api/passenger/{id}/feedback/received")
    suspend fun getPassengerReceivedFeedback(@Path("id") id: Int) : RestResult<List<Feedback>>

    @GET("api/passenger/{id}/feedback/sent")
    suspend fun getPassengerSentFeedback(@Path("id") id: Int) : RestResult<List<Feedback>>
}