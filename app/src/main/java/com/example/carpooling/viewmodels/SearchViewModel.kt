package com.example.carpooling.viewmodels

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.carpooling.data.RestRepository
import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.data.restful.RestError
import com.example.carpooling.data.restful.RestException
import com.example.carpooling.data.restful.RestSuccess
import com.example.carpooling.data.restful.requests.ActiveRidesRequest
import kotlinx.coroutines.*

class SearchViewModel(private val restRepository: RestRepository) : ViewModel() {

    private val _searchQuery = MutableLiveData<ActiveRidesRequest>()
    private var job: Job? = null

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _from = MutableLiveData<Address>()
    val from: LiveData<Address> = _from

    private val _to = MutableLiveData<Address>()
    val to: LiveData<Address> = _to

    fun setQuery(priceLower: Double, priceUpper: Double, smoking: Boolean, luggage: Boolean, silence: Boolean) {
        val request = ActiveRidesRequest(
            fromLat = from.value!!.latitude,
            fromLng = from.value!!.longitude,
            toLat = to.value!!.latitude,
            toLng = to.value!!.longitude,
            date = date.value!!,
            time = time.value!!,
            priceLower = priceLower,
            priceUpper = priceUpper,
            smokingAllowed = smoking,
            luggageAllowed = luggage,
            silentRide = silence
        )
        _searchQuery.value = request
    }

    fun executeQuery() : LiveData<List<ActiveRide>> {
        val rides = liveData {
            when (val result = restRepository.query(_searchQuery.value!!)) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(listOf())
                is RestException -> emit(listOf())
            }
        }
        return rides
    }

    fun getActiveRideById(id: Long) : LiveData<ActiveRide?> {
        val ride = liveData {
            when (val result = restRepository.getActiveRide(id)) {
                is RestSuccess -> emit(result.data)
                is RestError -> emit(null)
                is RestException -> emit(null)
            }
        }
        return ride
    }

    fun setDate(dateString: String) {
        _date.value = dateString
    }

    fun setTime(timeString: String) {
        _time.value = timeString
    }

    fun setFrom(location: Address) {
        _from.value = location
    }

    fun setTo(location: Address) {
        _to.value = location
    }
}