package com.example.carpooling.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carpooling.data.ActiveRidesRepository
import com.example.carpooling.data.BookingRepository
import com.example.carpooling.data.UserRepository
import com.example.carpooling.data.MyRidesRepository

class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass) {
        UserViewModel::class.java -> UserViewModel(userRepository = UserRepository())
        BookingViewModel::class.java -> BookingViewModel(bookingRepository = BookingRepository())
        ActiveRidesViewModel::class.java -> ActiveRidesViewModel(activeRidesRepository = ActiveRidesRepository())
        PublishViewModel::class.java -> PublishViewModel(activeRidesRepository = ActiveRidesRepository())
        MyRidesViewModel::class.java -> MyRidesViewModel(myRidesRepository = MyRidesRepository())
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T
}