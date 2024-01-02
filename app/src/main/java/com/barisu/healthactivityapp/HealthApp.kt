package com.barisu.healthactivityapp

import SocketForegroundService
import android.app.Application

class HealthApp : Application() {
    lateinit var socketForegroundService: SocketForegroundService

    override fun onCreate() {
        super.onCreate()
        socketForegroundService = SocketForegroundService()
    }
}
