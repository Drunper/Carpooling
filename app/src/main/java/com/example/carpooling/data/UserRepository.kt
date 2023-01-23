package com.example.carpooling.data

import com.example.carpooling.data.model.*
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.data.restful.RestResult
import com.example.carpooling.data.restful.requests.LoginRequest
import com.example.carpooling.data.restful.requests.SendPushTokenRequest
import com.example.carpooling.data.restful.requests.UpdateProfileRequest


class UserRepository() {
    fun logout() {
        ApiClient.resetApiService()
    }

/*    suspend fun login(request: LoginRequest): LoginResult {
        val apiService = ApiClient.getLoginApiService(email, password)
        val call = apiService.login()
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200) {
                    Log.d(AUTH_SUCCESS, "signInWithEmail:success")
                    ApiClient.setApiService(email, password)
                    val user = response.body()
                    liveData.value = Result.success(user)
                } else if (response.code() == 401) {
                    Log.w(AUTH_FAILURE, "signInWithEmail:failure")
                    liveData.value =
                        Result.error(Error(code = 401, "Username o password non validi"))
                } else {
                    Log.w(AUTH_FAILURE, "signInWithEmail:failure")
                    liveData.value =
                        Result.error(Error(code = -1, "Errore durante l'autenticazione"))
                    // TODO: rimuovere magic strings
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                liveData.value = Result.error(Error(code = -2, t.toString()))
            }
        })
        return liveData
    }*/

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