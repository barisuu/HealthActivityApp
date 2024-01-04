package com.barisu.healthactivityapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePasswordViewModel : ViewModel() {
    private var currentPassword: String = "" //TODO to be retrieved from server.

    fun changePassword(
        newPassword: String,
        confirmPassword: String,
        activeSocket: SocketConnection,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO){
            // Check if passwords match.
            if (newPassword == confirmPassword) {
                updatePassword(newPassword,activeSocket)
                withContext(Dispatchers.Main){
                    onSuccess.invoke()
                }

            } else {
                withContext(Dispatchers.Main) {
                    onError.invoke()
                }
            }
        }

    }

    private suspend fun updatePassword(newPassword: String, activeSocket: SocketConnection) {
        activeSocket.send("chgPass-${newPassword}")
        currentPassword = newPassword
    }
}


