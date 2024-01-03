package com.barisu.healthactivityapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserScreenViewModel() : ViewModel() {

    private val _anomaly = MutableLiveData(false)

    val anomaly: LiveData<Boolean> = _anomaly

    init {
        observeMessageReceived()
    }

    private fun observeMessageReceived() {
        //if (MessageRepository.currentMode=="user")
        //{
        //    MessageRepository.messageReceived.observeForever { message ->
        //        _anomaly.value = parseMessage(message)
        //    }
        //}

    }
    private fun parseMessage(message: String): Boolean {
        // TODO implement correct parsing for anomaly
        val parts = message.split("-")
        val anomaly = parts[0].trim()
        if (anomaly=="yes"){
            return true
        }
            return false
    }
}