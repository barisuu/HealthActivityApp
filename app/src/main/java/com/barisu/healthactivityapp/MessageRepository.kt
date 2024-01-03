package com.barisu.healthactivityapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object MessageRepository {
    private val _messageReceived = MutableLiveData<String>()
    val messageReceived: LiveData<String>
        get() = _messageReceived

    fun processReceivedData(data: String) {
        _messageReceived.postValue(data)
    }
}
