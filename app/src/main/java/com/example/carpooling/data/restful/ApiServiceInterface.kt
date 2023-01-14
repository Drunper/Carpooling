package com.example.carpooling.data.restful

import com.example.carpooling.data.model.*
import com.example.carpooling.data.restful.requests.ActiveRidePublishRequest
import com.example.carpooling.data.restful.requests.ActiveRidesRequest
import com.example.carpooling.data.restful.requests.SendFeedbackRequest
import com.example.carpooling.data.restful.requests.UpdateProfileRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceInterface {

    @POST("api/login")
    fun login(): Call<User>

    @GET("api/active_ride/{id}")
    suspend fun getActiveRide(@Path("id") id: Long) : ActiveRide

    @GET("api/old_ride/{id}")
    suspend fun getOldRide(@Path("id") id: Long) : OldRide

    @POST("api/active_rides/{page}")
    suspend fun getActiveRides(@Body requestBody: ActiveRidesRequest, @Path("page") page: Long): List<ActiveRide>

    @GET("api/active_rides/{id}/participants")
    suspend fun getActiveRideParticipants(@Path("id") id: Long) : List<User>

    @GET("api/rider/old_rides/{page}")
    suspend fun getRiderOldRides(@Path("page") page: Long) : List<OldRide>

    @GET("api/rider/active_rides/{page}")
    suspend fun getRiderActiveRides(@Path("page") page: Long) : List<ActiveRide>

    @GET("api/participant/active_rides/{page}")
    suspend fun getParticipantActiveRides(@Path("page") page: Long) : List<ActiveRide>

    @GET("api/participant/old_rides/{page}")
    suspend fun getParticipantOldRides(@Path("page") page: Long) : List<OldRide>

    @GET("api/user/feedbacks/received/{page}")
    suspend fun getReceivedFeedbacks(@Path("page") page: Long) : List<Feedback>

    @GET("api/user/feedbacks/sent/{page}")
    suspend fun getSentFeedbacks(@Path("page") page: Long) : List<Feedback>

    @GET("api/old_ride/{id}/feedbacks/received")
    suspend fun getOldRideReceivedFeedbacks(@Path("id") id: Long) : List<Feedback>

    @GET("api/old_ride/{id}/feedbacks/sent")
    suspend fun getOldRideSentFeedbacks(@Path("id") id: Long) : List<Feedback>

    @POST("api/active_ride/publish")
    suspend fun publishActiveRide(@Body requestBody: ActiveRidePublishRequest) : Success

    @GET("api/rider/rating")
    suspend fun getUserRiderRating() : RiderRating

    @GET("api/participant/rating")
    suspend fun getUserPassengerRating() : PassengerRating

    @POST("api/active_ride/{id}/book")
    suspend fun bookActiveRide(@Path("id") id: Long) : Success

    @POST("api/active_ride/{id}/cancel_booking")
    suspend fun cancelBookingActiveRide(@Path("id") id: Long) : Success

    @DELETE("api/active_ride/{id}/delete")
    suspend fun deleteActiveRide(@Path("id") id: Long) : Success

    @POST("api/send/feedback")
    suspend fun sendFeedback(@Body requestBody: SendFeedbackRequest) : Success

    @GET("api/rider/feedbacks/amount")
    suspend fun getNumberOfRiderFeedbacks() : Amount

    @GET("api/participant/feedbacks/amount")
    suspend fun getNumberOfParticipantFeedbacks() : Amount

    @GET("api/rider/old_rides/amount")
    suspend fun getNumberOfRiderOldRides() : Amount

    @GET("api/participant/old_rides/amount")
    suspend fun getNumberOfParticipantOldRides() : Amount

    @PATCH("api/user/update")
    suspend fun updateProfile(@Body requestBody: UpdateProfileRequest) : Success

    /*@GET("api/active_ride/{id}/check/new_booking")
    suspend fun checkNewBooking()*/
}