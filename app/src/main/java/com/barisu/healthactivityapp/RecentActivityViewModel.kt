package com.barisu.healthactivityapp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecentActivityViewModel: ViewModel() {

    private val _graphData = MutableLiveData<FloatArray>()

    val graphData: LiveData<FloatArray> = _graphData


    init {
        observeMessageReceived()
    }
    private fun observeMessageReceived() {
        MessageRepository.messageReceived.observeForever { message ->
            val arrayData = parseMessage(message)
            _graphData.value = arrayData

        }
    }
    fun getGraphData(context: Context){
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "lastPred")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }
    private fun parseMessage(message: String): FloatArray {
        val parts = message.split(":")
        if(parts.size ==2) {
            val (messageType, dataString) = parts
            if (messageType == "lastpred") {
                val dataArray = dataString.substringAfter("[").substringBefore("]")
                    .split(", ").map { it.toFloat() }.toFloatArray()
                println("Data type is: $messageType")
                println("Array is: $dataArray")

                return dataArray
            }
        }

        return floatArrayOf()
    }


}