package com.example.carpooling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carpooling.data.ActiveRidesRepository
import com.example.carpooling.data.model.ActiveRide
import com.example.carpooling.data.restful.RestError
import com.example.carpooling.data.restful.RestException
import com.example.carpooling.data.restful.RestSuccess
import com.example.carpooling.data.restful.requests.ActiveRidesRequest
import kotlinx.coroutines.*

class ActiveRidesViewModel(private val activeRidesRepository: ActiveRidesRepository) : ViewModel() {

    private val _activeRides = MutableLiveData<List<ActiveRide>>()
    val activeRides: LiveData<List<ActiveRide>> = _activeRides

    private val _searchQuery = MutableLiveData<ActiveRidesRequest>()

    private var job: Job? = null

    fun setQuery(request: ActiveRidesRequest) {
        _searchQuery.value = request
    }

    fun executeQuery() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val rides = when (val result = activeRidesRepository.query(_searchQuery.value!!, 1)) {
                is RestSuccess -> {
                    result.data
                }
                is RestError -> {
                    listOf()
                }
                is RestException -> {
                    listOf()
                }
            }
            withContext(Dispatchers.Main) {
                _activeRides.value = rides
            }
        }
    }

    fun getActiveRideById(id: Long) : ActiveRide? {
        return _activeRides.value?.firstOrNull {
            it.id == id
        }
    }
}