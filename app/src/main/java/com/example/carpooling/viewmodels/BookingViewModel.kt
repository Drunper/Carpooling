package com.example.carpooling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carpooling.data.BookingRepository
import kotlinx.coroutines.*

class BookingViewModel(private val bookingRepository: BookingRepository) : ViewModel() {

    private val _bookRideResult = MutableLiveData<Boolean>()
    val bookRideResult: LiveData<Boolean> = _bookRideResult

    private val _cancelBookingRideResult = MutableLiveData<Boolean>()
    val cancelBookingRideResult: LiveData<Boolean> = _cancelBookingRideResult

    var job: Job? = null

    fun bookRide(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val success = bookingRepository.bookRide(id)
            withContext(Dispatchers.Main) {
                _bookRideResult.value = success?.success ?: false
            }
        }
    }

    fun cancelBookingRide(id: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val success = bookingRepository.cancelBooking(id)
            withContext(Dispatchers.Main) {
                _cancelBookingRideResult.value = success?.success ?: false
            }
        }
    }
}