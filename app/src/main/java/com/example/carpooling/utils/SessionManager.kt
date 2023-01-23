package com.example.carpooling.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.carpooling.R

class SessionManager(context: Context) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(context.getString(
        R.string.app_name), Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN, null)
    }

    fun deleteAuthToken() {
        val editor = sharedPreferences.edit()
        editor.remove(AUTH_TOKEN)
        editor.apply()
    }

    companion object {
        const val AUTH_TOKEN = "auth_token"
    }
}