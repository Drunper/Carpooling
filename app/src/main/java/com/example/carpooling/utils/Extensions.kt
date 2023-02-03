package com.example.carpooling.utils

import android.content.Context
import android.content.res.Resources
import android.location.Address
import android.view.View
import androidx.compose.ui.res.stringResource
import androidx.preference.PreferenceManager
import com.example.carpooling.R
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun View.showSnackbar(
    view: View,
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(view, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    } else {
        snackbar.show()
    }
}

fun String.convertDate(
    fromPattern: String,
    toPattern: String
): String {
    val sdfToDate = SimpleDateFormat(fromPattern, Locale.getDefault())
    val toFormat = sdfToDate.parse(this)

    val sdf = SimpleDateFormat(toPattern, Locale.getDefault())
    return sdf.format(toFormat)
}

fun Address.getString(): String {
    return if (thoroughfare != null) {
        if (subThoroughfare != null)
            "$thoroughfare, $subThoroughfare, $locality"
        else
            "$thoroughfare, $locality"
    }
    else {
        locality
    }
}

fun Double.formatCurrency(context: Context): String {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.minimumFractionDigits = 2
    format.currency =
        Currency.getInstance(sharedPreferences.getString("currency", "EUR"))
    return format.format(this)
}