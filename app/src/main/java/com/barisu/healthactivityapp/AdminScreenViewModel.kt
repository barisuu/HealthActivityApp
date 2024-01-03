package com.barisu.healthactivityapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminScreenViewModel:ViewModel() {

    fun updateActivity(
        socketConnection: SocketConnection,
        activityType:String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            socketConnection.send(activityType)
        }
    }
}