package com.example.carpooling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.carpooling.data.MyRidesRepository
import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.data.model.OldRide
import kotlinx.coroutines.*

class MyRidesViewModel(private val myRidesRepository: MyRidesRepository) : ViewModel() {

    private val _deleteRideResult = MutableLiveData<Boolean>()
    val deleteRideResult: LiveData<Boolean> = _deleteRideResult

    private val _cancelBookingResult = MutableLiveData<Boolean>()
    val cancelBookingResult: LiveData<Boolean> = _cancelBookingResult

    var job: Job? = null

    fun getParticipantActiveRides(page: Long) : LiveData<List<ActiveRide>> {
        val rides = liveData {
            val result = myRidesRepository.getParticipantActiveRides(page)
            emit(result)
        }
        return rides
    }

    fun getRiderActiveRides(page: Long) : LiveData<List<ActiveRide>> {
        val rides = liveData {
            val result = myRidesRepository.getRiderActiveRides(page)
            emit(result)
        }
        return rides
    }

    fun getParticipantOldRides(page: Long) : LiveData<List<OldRide>> {
        val rides = liveData {
            val result = myRidesRepository.getParticipantOldRides(page)
            emit(result)
        }
        return rides
    }

    fun getRiderOldRides(page: Long) : LiveData<List<OldRide>> {
        val rides = liveData {
            val result = myRidesRepository.getRiderOldRides(page)
            emit(result)
        }
        return rides
    }

    fun getActiveRideByID(id: Long) : LiveData<ActiveRide> {
        val ride = liveData {
            val result = myRidesRepository.getActiveRideByID(id)
            emit(result)
        }
        return ride
    }

    fun getOldRideByID(id: Long) : LiveData<OldRide> {
        val ride = liveData {
            val result = myRidesRepository.getOldRideByID(id)
            emit(result)
        }
        return ride
    }

    fun deleteRide(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val success = myRidesRepository.deleteRide(id)
            withContext(Dispatchers.Main) {
                _deleteRideResult.value = success.success
            }
        }
    }

    fun cancelBooking(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val success = myRidesRepository.cancelBooking(id)
            withContext(Dispatchers.Main) {
                _cancelBookingResult.value = success.success
            }
        }
    }
}