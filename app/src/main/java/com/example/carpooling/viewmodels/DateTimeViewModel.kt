package com.example.carpooling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DateTimeViewModel : ViewModel() {
    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    fun setDate(dateString: String) {
        _date.value = dateString
    }

    fun setTime(timeString: String) {
        _time.value = timeString
    }
}