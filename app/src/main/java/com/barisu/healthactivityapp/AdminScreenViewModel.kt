package com.barisu.healthactivityapp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class AdminScreenViewModel:ViewModel() {

    fun disconnect(context: Context){
        informServer(context)
        println("In viewmodel disconnect")
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.STOP.toString()
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }

    //TODO Fix sync issue with disconnection.
    fun informServer(context: Context){
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "disconnect")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }
}