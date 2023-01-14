package com.example.carpooling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.carpooling.data.UserRepository
import com.example.carpooling.data.Result

import com.example.carpooling.R
import com.example.carpooling.data.model.PassengerRating
import com.example.carpooling.data.model.RiderRating
import com.example.carpooling.data.model.Success
import com.example.carpooling.data.model.User
import com.example.carpooling.data.restful.requests.UpdateProfileRequest
import com.example.carpooling.ui.login.LoginFormState
import kotlinx.coroutines.*

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var job: Job? = null

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _user = MutableLiveData(
        User(
            email = "not",
            username = "logged",
            profilePicReference = "in",
            bio = "!"
        )
    )
    val user: LiveData<User> = _user

    private val _driverRating = MutableLiveData<RiderRating>()
    val driverRating: LiveData<RiderRating> = _driverRating

    private val _passengerRating = MutableLiveData<PassengerRating>()
    val passengerRating: LiveData<PassengerRating> = _passengerRating

    private val _updateProfileResult = MutableLiveData<Success>()
    val updateProfileResult: LiveData<Success> = _updateProfileResult

    fun login(email: String, password: String, rememberMe: Boolean): LiveData<Result<User>> {
        return userRepository.login(email, password, rememberMe)
    }

    fun logout() {
        userRepository.logout()
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun updateUser(user: User) {
        _user.value = user
    }

    fun getUserDriverRating() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val driverRating = userRepository.getDriverRating()
            withContext(Dispatchers.Main) {
                _driverRating.value = driverRating
            }
        }
    }

    fun getUserPassengerRating() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val passengerRating = userRepository.getPassengerRating()
            withContext(Dispatchers.Main) {
                _passengerRating.value = passengerRating
            }
        }
    }

    fun updateProfile(request: UpdateProfileRequest) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = userRepository.updateProfile(request)
            withContext(Dispatchers.Main) {
                if (result.success) {
                    val updatedUser = User(
                        email = _user.value!!.email,
                        username = request.username,
                        profilePicReference = _user.value!!.profilePicReference,
                        bio = request.bio
                    )
                    updateUser(updatedUser)
                }
                _updateProfileResult.value = result
            }
        }
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
}