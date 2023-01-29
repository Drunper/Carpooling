package com.example.carpooling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.carpooling.data.RestRepository
import com.example.carpooling.data.model.*
import com.example.carpooling.data.restful.RestError
import com.example.carpooling.data.restful.RestException
import com.example.carpooling.data.restful.RestSuccess
import com.example.carpooling.data.restful.requests.SendFeedbackRequest
import kotlinx.coroutines.*

class MyRidesViewModel(private val restRepository: RestRepository) : ViewModel() {

    private val _deleteRideResult = MutableLiveData<Boolean>()
    val deleteRideResult: LiveData<Boolean> = _deleteRideResult

    private val _cancelBookingResult = MutableLiveData<Boolean>()
    val cancelBookingResult: LiveData<Boolean> = _cancelBookingResult

    private var job: Job? = null

    fun getPassengerActiveRides() : LiveData<List<ActiveRide>> {
        val rides = liveData {
            when (val result = restRepository.getPassengerActiveRides()) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return rides
    }

    fun getDriverActiveRides() : LiveData<List<ActiveRide>> {
        val rides = liveData {
            when (val result = restRepository.getDriverActiveRides()) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return rides
    }

    fun getPassengerOldRides() : LiveData<List<OldRide>> {
        val rides = liveData {
            when (val result = restRepository.getPassengerOldRides()) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return rides
    }

    fun getDriverOldRides() : LiveData<List<OldRide>> {
        val rides = liveData {
            when (val result = restRepository.getDriverOldRides()) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return rides
    }

    fun getActiveRideByID(id: Long): LiveData<ActiveRide> {
        val ride = liveData {
            when (val result = restRepository.getActiveRideByID(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> {}
                is RestException -> {}
            }
        }
        return ride
    }

    fun getOldRideByID(id: Long): LiveData<OldRide> {
        val ride = liveData {
            when (val result = restRepository.getOldRideByID(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> {}
                is RestException -> {}
            }
        }
        return ride
    }

    fun deleteRide(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val value = when (val result = restRepository.deleteRide(id)) {
                is RestSuccess -> result.data
                is RestError -> Success(success = false)
                is RestException -> Success(success = false)
            }
            withContext(Dispatchers.Main) {
                _deleteRideResult.value = value.success
            }
        }
    }

    fun cancelBooking(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val value = when (val result = restRepository.cancelBooking(id)) {
                is RestSuccess -> result.data
                is RestError -> Success(success = false)
                is RestException -> Success(success = false)
            }
            withContext(Dispatchers.Main) {
                _cancelBookingResult.value = value.success
            }
        }
    }

    fun oldRideReceivedFeedbacks(id: Long): LiveData<List<Feedback>> {
        val feedbacks = liveData {
            when (val result = restRepository.getOldRideReceivedFeedbacks(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return feedbacks
    }

    fun oldRideSentFeedbacks(id: Long): LiveData<List<Feedback>> {
        val feedbacks = liveData {
            when (val result = restRepository.getOldRideSentFeedbacks(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return feedbacks
    }

    fun sendFeedback(request: SendFeedbackRequest): LiveData<Boolean> {
        val success = liveData {
            when (val result = restRepository.sendFeedback(request)) {
                is RestSuccess -> emit(result.data.success)
                is RestError -> emit(false)
                is RestException -> emit(false)
            }
        }
        return success
    }

    fun getMissingUserFeedbacks(id: Long): LiveData<List<User>> {
        val users = liveData {
            when (val result = restRepository.getMissingFeedbackUsers(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return users
    }
}