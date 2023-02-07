package com.example.carpooling.utils.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.carpooling.R

enum class Channel(val channelName: String) {
    NEW_BOOKING("new_booking_channel"),
    NEW_CANCEL("new_cancel_channel"),
    DELETE_RIDE("delete_ride_channel")
}

object NotificationHandler {
    fun sendNotification(context: Context, channel: Channel, rideId: Int?) {
        val destination = when (channel) {
            Channel.DELETE_RIDE -> R.id.myRidesFragment
            else -> R.id.driverRideFragment
        }

        var deepLinkBuilder = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_nav_graph)
            .setDestination(destination)

        if (rideId != null && (channel == Channel.NEW_BOOKING || channel == Channel.NEW_CANCEL)) {
            val args = Bundle()
            args.putInt("rideId", rideId)
            deepLinkBuilder = deepLinkBuilder.setArguments(args)
        }

        val pendingIntent = deepLinkBuilder
            .createPendingIntent()

        val contentTitle = when (channel) {
            Channel.NEW_BOOKING -> context.getString(R.string.notif_new_booking_title)
            Channel.NEW_CANCEL -> context.getString(R.string.notif_new_cancel_title)
            Channel.DELETE_RIDE -> context.getString(R.string.notif_delete_ride_title)
        }

        val contentText = when (channel) {
            Channel.NEW_BOOKING -> context.getString(R.string.notif_new_booking_desc)
            Channel.NEW_CANCEL -> context.getString(R.string.notif_new_cancel_desc)
            Channel.DELETE_RIDE -> context.getString(R.string.notif_delete_ride_desc)
        }

        createNotificationChannels(context)

        val builder = NotificationCompat.Builder(context, channel.channelName)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(VISIBILITY_PUBLIC) // Show on lock screen

        with(NotificationManagerCompat.from(context)) {
            notify(0, builder.build())
        }
    }

    private fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannels
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelNewBooking = NotificationChannel(Channel.NEW_BOOKING.channelName, context.getString(R.string.channel_new_booking), importance)
            channelNewBooking.description = context.getString(R.string.channel_new_booking_desc)

            val channelNewCancel = NotificationChannel(Channel.NEW_CANCEL.channelName, context.getString(R.string.channel_new_cancel), importance)
            channelNewCancel.description = context.getString(R.string.channel_new_cancel_desc)

            val channelDeleteRide = NotificationChannel(Channel.DELETE_RIDE.channelName, context.getString(R.string.channel_delete_ride), importance)
            channelDeleteRide.description = context.getString(R.string.channel_delete_ride_desc)

            val channels = listOf(channelNewBooking, channelNewCancel, channelDeleteRide)

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannels(channels)
        }
    }
}