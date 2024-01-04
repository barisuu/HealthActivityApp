package com.barisu.healthactivityapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class HealthApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val constantChannel = NotificationChannel(
                "socket_channel",
                "Socket Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(constantChannel)
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val anomalyChannel = NotificationChannel(
                "anomaly_channel",
                "Anomaly Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(anomalyChannel)
        }
    }
}
