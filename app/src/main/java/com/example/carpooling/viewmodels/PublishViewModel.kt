package com.example.carpooling.viewmodels

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carpooling.data.RestRepository
import com.example.carpooling.data.model.Success
import com.example.carpooling.data.restful.RestError
import com.example.carpooling.data.restful.RestException
import com.example.carpooling.data.restful.RestSuccess
import com.example.carpooling.data.restful.requests.ActiveRidePublishRequest
import kotlinx.coroutines.*

class PublishViewModel(private val restRepository: RestRepository) : ViewModel() {

    private var job: Job? = null

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _departureTime = MutableLiveData<String>()
    val departureTime: LiveData<String> = _departureTime

    private val _arrivalTime = MutableLiveData<String>()
    val arrivalTime: LiveData<String> = _arrivalTime

    private val _locationFormState = MutableLiveData<Boolean>()
    val locationFormState: LiveData<Boolean> = _locationFormState

    private val _timeFormState = MutableLiveData<Boolean>()
    val timeFormState: LiveData<Boolean> = _timeFormState

    private val _from = MutableLiveData<Address>()
    val from: LiveData<Address> = _from

    private val _to = MutableLiveData<Address>()
    val to: LiveData<Address> = _to

    private val _availableSeats = MutableLiveData(1)
    val availableSeats: LiveData<Int> = _availableSeats

    private val _publishResult = MutableLiveData<Boolean>()
    val publishResult: LiveData<Boolean> = _publishResult

    private val _smoking = MutableLiveData(false)
    val smoking: LiveData<Boolean> = _smoking

    private val _luggage = MutableLiveData(false)
    val luggage: LiveData<Boolean> = _luggage

    private val _silent = MutableLiveData(false)
    val silent: LiveData<Boolean> = _silent

    private val _price = MutableLiveData<Double?>()
    val price: LiveData<Double?> = _price

    private val _notes = MutableLiveData<String>()
    var notes: LiveData<String> = _notes

    fun setDate(value: String) {
        _date.value = value
    }

    fun setDepartureTime(value: String) {
        _departureTime.value = value
        if (_arrivalTime.value != null) {
            _timeFormState.value = true
        }
    }

    fun setArrivalTime(value: String) {
        _arrivalTime.value = value
        if (_departureTime.value != null) {
            _timeFormState.value = true
        }
    }

    fun setFrom(location: Address) {
        _from.value = location
        if (_to.value != null) {
            _locationFormState.value = true
        }
    }

    fun setTo(location: Address) {
        _to.value = location
        if (_from.value != null) {
            _locationFormState.value = true
        }
    }

    fun setNotes(value: String) {
        _notes.value = value
    }

    fun setPrice(value: Double?) {
        _price.value = value
    }

    fun increaseAvailableSeats() {
        _availableSeats.value = _availableSeats.value?.plus(1)
    }

    fun decreaseAvailableSeats() {
        _availableSeats.value = _availableSeats.value?.minus(1)
    }

    fun publish() {
        val request = ActiveRidePublishRequest(
            fromLat = _from.value!!.latitude,
            fromLng = _from.value!!.longitude,
            toLat = _to.value!!.latitude,
            toLng = _to.value!!.longitude,
            date = _date.value!!,
            departureTime = _departureTime.value!!,
            arrivalTime = _arrivalTime.value!!,
            price = _price.value!!,
            availableSeats = _availableSeats.value!!,
            smokingAllowed = _smoking.value!!,
            luggageAllowed = _luggage.value!!,
            silentRide = _silent.value!!,
            addNotes = notes.value!!
        )
        job = CoroutineScope(Dispatchers.IO).launch {
            val value = when (val result = restRepository.publish(request)) {
                is RestSuccess -> result.data
                is RestError -> Success(success = false)
                is RestException -> Success(success = false)
            }
            withContext(Dispatchers.Main) {
                _publishResult.value = value.success
            }
        }
    }
}