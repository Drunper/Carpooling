package com.example.carpooling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.carpooling.data.RestRepository
import com.example.carpooling.data.model.Success
import com.example.carpooling.data.restful.RestError
import com.example.carpooling.data.restful.RestException
import com.example.carpooling.data.restful.RestSuccess
import kotlinx.coroutines.*

class BookingViewModel(private val restRepository: RestRepository) : ViewModel() {

    private val _bookRideResult = MutableLiveData<Boolean>()
    val bookRideResult: LiveData<Boolean> = _bookRideResult

    private var job: Job? = null

    fun bookRide(id: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val success = when (val result = restRepository.bookRide(id)) {
                is RestSuccess -> result.data
                is RestError -> Success(success = false)
                is RestException -> Success(success = false)
            }
            withContext(Dispatchers.Main) {
                _bookRideResult.value = success.success
            }
        }
    }

    fun bookRide2(id: Int): LiveData<Boolean> {
        val success = liveData {
            when (val result = restRepository.bookRide(id)) {
                is RestSuccess -> emit(result.data.success)
                is RestError -> emit(false)
                is RestException -> emit(false)
            }
        }
        return success
    }
}