package com.example.carpooling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.liveData
import android.util.Log
import com.example.carpooling.R
import com.example.carpooling.data.RestRepository
import com.example.carpooling.data.model.*
import com.example.carpooling.data.restful.*
import com.example.carpooling.data.restful.requests.LoginRequest
import com.example.carpooling.data.restful.requests.SendPushTokenRequest
import com.example.carpooling.data.restful.requests.UpdateProfileRequest
import com.example.carpooling.ui.login.LoginFormState
import kotlinx.coroutines.*

class UserViewModel(private val restRepository: RestRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private var job: Job? = null
    var rememberMe: Boolean = false

    fun login(email: String, password: String): LiveData<LoginResult> {
        val loginResult = liveData {
            val request = LoginRequest(email, password)
            when (val result = restRepository.login(request)) {
                is RestSuccess -> {
                    Log.d(AUTH_SUCCESS, "signInWithEmail:success")
                    emit(result.data)
                }
                is RestError -> {
                    if (result.statusCode == 401) {
                        Log.d(AUTH_FAILURE, "signInWithEmail:failure")
                        val loginResult = LoginResult(token = "401", user = null)
                        emit(loginResult)
                    }
                    else {
                        Log.d(AUTH_ERROR, "signInWithEmail:error")
                        Log.d(AUTH_ERROR_CODE, result.statusCode.toString())
                        result.message?.let { Log.d(AUTH_ERROR_MESSAGE, it) }
                        val loginResult = LoginResult(token = "error", user = null)
                        emit(loginResult)
                    }
                }
                is RestException -> {
                    Log.d(AUTH_EXCEPTION, "signInWithEmail:exception")
                    result.exception.message?.let { Log.d(AUTH_EXCEPTION_MESSAGE, it) }
                    val loginResult = LoginResult(token = "exception", user = null)
                    emit(loginResult)
                }
            }
        }
        return loginResult
    }

    fun initUser(token: String) {
        ApiClient.setApiService(token)
        job = CoroutineScope(Dispatchers.IO).launch {
            val value = when (val result = restRepository.getUser()) {
                is RestSuccess -> result.data
                is RestError -> null
                is RestException -> null
            }
            withContext(Dispatchers.Main) {
                _user.value = value
            }
        }
    }

    fun sendPushToken(pushToken: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val sendPushTokenRequest = SendPushTokenRequest(pushToken = pushToken)
            when (val result = restRepository.sendToken(sendPushTokenRequest)) {
                is RestSuccess -> Log.d("sendPushToken", "success")
                is RestError -> Log.d("sendPushToken", "error")
                is RestException -> Log.d("sendPushToken", "exception")
            }
        }
    }

    fun logout() {
        job = CoroutineScope(Dispatchers.IO).launch {
            when (val result = restRepository.deleteToken()) {
                is RestSuccess -> Log.d("deletePushToken", "success")
                is RestError -> Log.d("deletePushToken", "error")
                is RestException -> Log.d("deletePushToken", "exception")
            }
            withContext(Dispatchers.Main) {
                _user.value = null
                restRepository.logout()
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.error_invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.error_invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun updateUser(user: User?) {
        _user.value = user
    }

    fun updateProfile(request: UpdateProfileRequest): LiveData<Boolean> {
        val result = liveData {
            val value = when (val result = restRepository.updateProfile(request)) {
                is RestSuccess -> result.data.success
                is RestError -> false
                is RestException -> false
            }
            withContext(Dispatchers.Main) {
                if (value) {
                    val updatedUser = _user.value!!.copy(username = request.username, bio = request.bio)
                    updateUser(updatedUser)
                }
            }
            emit(value)
        }
        return result
    }

    fun getUserById(id: Int): LiveData<User> {
        val value = liveData {
            when (val result = restRepository.getUserById(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> {}
                is RestException -> {}
            }
        }
        return value
    }

    fun getUserStats(id: Int): LiveData<UserStats> {
        val value = liveData {
            when (val result = restRepository.getUserStats(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> {}
                is RestException -> {}
            }
        }
        return value
    }

    fun getDriverReceivedFeedback(id: Int): LiveData<List<Feedback>> {
        val value = liveData {
            when (val result = restRepository.getDriverReceivedFeedback(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return value
    }

    fun getDriverSentFeedback(id: Int): LiveData<List<Feedback>> {
        val value = liveData {
            when (val result = restRepository.getDriverSentFeedback(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return value
    }

    fun getPassengerReceivedFeedback(id: Int): LiveData<List<Feedback>> {
        val value = liveData {
            when (val result = restRepository.getPassengerReceivedFeedback(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return value
    }

    fun getPassengerSentFeedback(id: Int): LiveData<List<Feedback>> {
        val value = liveData {
            when (val result = restRepository.getPassengerSentFeedback(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return value
    }

    fun getProfilePicReference(): String {
        return _user.value!!.profilePicReference
    }

    // A placeholder username validation check
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    companion object {
        const val AUTH_SUCCESS = "AuthSuccess"
        const val AUTH_FAILURE = "AuthFailure"
        const val AUTH_ERROR_CODE = "AuthErrorCode"
        const val AUTH_ERROR_MESSAGE = "AuthErrorMessage"
        const val AUTH_ERROR = "AuthError"
        const val AUTH_EXCEPTION = "AuthException"
        const val AUTH_EXCEPTION_MESSAGE = "AuthExceptionMessage"
    }
}