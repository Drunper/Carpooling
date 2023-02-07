package com.example.carpooling.utils.notifications

import android.util.Log
import androidx.preference.PreferenceManager
import com.example.carpooling.data.restful.ApiClient
import com.example.carpooling.data.restful.ApiServiceInterface
import com.example.carpooling.data.restful.requests.SendPushTokenRequest
import com.example.carpooling.utils.SessionManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        val authToken = SessionManager(applicationContext).getAuthToken()
        if (authToken != null) {
            ApiClient.setApiService(authToken)
            val service: ApiServiceInterface = ApiClient.getApiService()
            CoroutineScope(Dispatchers.IO).launch {
                service.sendPushToken(SendPushTokenRequest(pushToken = token))
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val notificationsEnabled = sharedPreference.getBoolean("notifications", false)
        if (notificationsEnabled) {
            if (message.data.isNotEmpty()) {
                when (message.data["channel"]) {
                    Channel.NEW_BOOKING.channelName -> {
                        val rideId = message.data["ride_id"]!!.toInt()
                        NotificationHandler.sendNotification(
                            applicationContext,
                            Channel.NEW_BOOKING,
                            rideId
                        )
                    }
                    Channel.NEW_CANCEL.channelName -> {
                        val rideId = message.data["ride_id"]!!.toInt()
                        NotificationHandler.sendNotification(
                            applicationContext,
                            Channel.NEW_CANCEL,
                            rideId
                        )
                    }
                    else -> NotificationHandler.sendNotification(
                        applicationContext,
                        Channel.DELETE_RIDE,
                        null
                    )
                }
            }
        }
        super.onMessageReceived(message)
    }
}