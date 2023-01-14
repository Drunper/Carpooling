package com.example.carpooling.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carpooling.data.model.*
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.data.restful.requests.UpdateProfileRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository() {
    fun logout() {
        ApiClient.resetApiService()
    }

    fun login(email: String, password: String, rememberMe: Boolean): LiveData<Result<User>> {
        val liveData = MutableLiveData<Result<User>>()
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
    }

    suspend fun getDriverRating(): RiderRating {
        return ApiClient.getApiService().getUserRiderRating()
    }

    suspend fun getPassengerRating(): PassengerRating {
        return ApiClient.getApiService().getUserPassengerRating()
    }

    suspend fun updateProfile(request: UpdateProfileRequest): Success {
        return ApiClient.getApiService().updateProfile(request)
    }

    companion object {
        const val AUTH_SUCCESS = "AuthSuccess"
        const val AUTH_FAILURE = "AuthFailure"
    }
}