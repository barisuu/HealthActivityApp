package com.barisu.healthactivityapp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangeContactInfoViewModel : ViewModel() {
    fun changeContactInfo(
        name: String,
        surname: String,
        title: String,
        telephone: String,
        address: String,
        context: Context,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO){
            // Check if passwords match.
            updateContactInfo(name,surname,title,telephone,address,context)
            withContext(Dispatchers.Main){
                onSuccess.invoke()
            }


        }

    }

    private fun updateContactInfo(name: String,
                                  surname: String,
                                  title: String,
                                  telephone: String,
                                  address: String,
                                  context: Context) {
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "chgPat-${name}-${surname}-${title}-${telephone}-${address}")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }

    fun closePage(context:Context){
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_DATA.toString()
        foregroundServiceIntent.putExtra("DATA", "close")
        ContextCompat.startForegroundService(context, foregroundServiceIntent)
    }
}


