package com.barisu.healthactivityapp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class RecentActivityViewModel: ViewModel() {

    fun getGraphData(context: Context){
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "lastPred")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }
}