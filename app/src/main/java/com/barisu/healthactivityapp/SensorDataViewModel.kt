package com.barisu.healthactivityapp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class SensorDataViewModel : ViewModel(){

    /*private fun observeMessageReceived() {
        MessageRepository.messageReceived.observeForever { message ->
            val arrayData = parseMessage(message)
            _sensorData.value = arrayData

        }
    }*/

    fun getSensorData(context: Context){
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "network")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }

   /* private fun parseMessage(message: String): CharArray {
        val parts = message.split(":")
        if(parts.size ==2) {
            val (messageType, dataString) = parts
            if (messageType == "lastpred") {
                val dataArray = dataString.substringAfter("[").substringBefore("]")
                    .split(", ")
                println("Data type is: $messageType")
                println("Array is: $dataArray")

                return dataArray
            }
        }

        return charArrayOf()
    }*/
}