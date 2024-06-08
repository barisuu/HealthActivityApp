package com.barisu.healthactivityapp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentActivityViewModel() : ViewModel() {

    private val _currentActivity = MutableLiveData<String>("Temp Activity")
    private val _currentCertainty = MutableLiveData<Int>(0)

    val currentActivity: LiveData<String> = _currentActivity
    val currentCertainty: LiveData<Int> = _currentCertainty

    init {
        observeMessageReceived()
    }

    private fun observeMessageReceived() {
        MessageRepository.messageReceived.observeForever { message ->
            val (activity, certainty) = parseMessage(message)
            _currentActivity.value = activity
            _currentCertainty.value = certainty
        }
    }

    private fun parseMessage(message: String): Pair<String, Int> {
        val parts = message.split("-")
        val activity = parts[0].trim()
        val certainty = if (parts.size > 1) parts[1].toIntOrNull() ?: 0 else 0
        if(activity == "connectionsuccess"){
            return "" to 0
        }
        return activity to certainty
    }

    fun updateActivity(context: Context) {
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "curAct")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }

    fun closePage(context: Context){
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "close")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }
}


