package com.barisu.healthactivityapp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
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
        context: Context,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO){
            // Check if passwords match.
            if (newPassword == confirmPassword) {
                updatePassword(newPassword,context)
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

    private fun updatePassword(newPassword: String, context: Context) {
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "chgPass-${newPassword}")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }

    fun closePage(context:Context){
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "close")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }
}


