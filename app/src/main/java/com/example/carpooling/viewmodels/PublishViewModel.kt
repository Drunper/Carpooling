package com.example.carpooling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carpooling.data.ActiveRidesRepository
import com.example.carpooling.data.restful.requests.ActiveRidePublishRequest
import kotlinx.coroutines.*

class PublishViewModel(private val activeRidesRepository: ActiveRidesRepository) : ViewModel() {

    private var job: Job? = null

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _availableSeats = MutableLiveData(1)
    val availableSeats: LiveData<Int> = _availableSeats

    private val _publishResult = MutableLiveData<Boolean>()
    val publishResult: LiveData<Boolean> = _publishResult

    var from: String? = null
    var to: String? = null
    var price: Double? = null
    var smoking: Boolean = false
    var luggage: Boolean = false
    var silent: Boolean = false
    var notes: String? = null

    fun setDate(value: String) {
        _date.value = value
    }

    fun setTime(value: String) {
        _time.value = value
    }

    fun increaseAvailableSeats() {
        _availableSeats.value = _availableSeats.value?.plus(1)
    }

    fun decreaseAvailableSeats() {
        _availableSeats.value = _availableSeats.value?.minus(1)
    }

    fun publish(fromLat: Double, fromLng: Double, toLat: Double, toLng: Double) {
        val request = ActiveRidePublishRequest(
            fromLat = fromLat,
            fromLng = fromLng,
            toLat = toLat,
            toLng = toLng,
            date = _date.value!!,
            time = _time.value!!,
            price = price!!,
            availableSeats = _availableSeats.value!!,
            smokingAllowed = smoking,
            luggageAllowed = luggage,
            silentRide = silent,
            addNotes = notes!!
        )
        job = CoroutineScope(Dispatchers.IO).launch {
            val success = activeRidesRepository.publish(request)
            withContext(Dispatchers.Main) {
                _publishResult.value = success?.success ?: false
            }
        }
    }
}