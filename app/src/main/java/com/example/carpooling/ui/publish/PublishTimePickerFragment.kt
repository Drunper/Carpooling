package com.example.carpooling.ui.publish

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class PublishTimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private val publishViewModel: PublishViewModel by activityViewModels {
        ViewModelFactory()
    }
    private val args: PublishTimePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val time = LocalTime.of(hourOfDay, minute)
        val dtf = DateTimeFormatter.ofPattern("HH:mm")
        if (args.departure)
            publishViewModel.setDepartureTime(time.format(dtf))
        else
            publishViewModel.setArrivalTime(time.format(dtf))
    }
}