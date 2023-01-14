package com.example.carpooling.ui.publish

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.carpooling.viewmodels.PublishViewModel
import com.example.carpooling.viewmodels.ViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PublishDatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val publishViewModel: PublishViewModel by activityViewModels {
        ViewModelFactory()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val date = LocalDate.of(year, month + 1, day)
        val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        publishViewModel.setDate(date.format(dtf))
    }
}