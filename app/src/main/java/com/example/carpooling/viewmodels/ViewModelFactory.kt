package com.example.carpooling.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carpooling.data.*

class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass) {
        UserViewModel::class.java -> UserViewModel(restRepository = RestRepository())
        SearchViewModel::class.java -> SearchViewModel(restRepository = RestRepository())
        PublishViewModel::class.java -> PublishViewModel(restRepository = RestRepository())
        MyRidesViewModel::class.java -> MyRidesViewModel(restRepository = RestRepository())
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T
}