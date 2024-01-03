package com.barisu.healthactivityapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        return activity to certainty
    }

    fun updateActivity(
        socketConnection: SocketConnection,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Assuming your sending function is named 'sendData'
                socketConnection.send("curAct")
            } catch (e: Exception) {
                // Log or handle the exception here
                e.printStackTrace()
            }

        }
    }
}


