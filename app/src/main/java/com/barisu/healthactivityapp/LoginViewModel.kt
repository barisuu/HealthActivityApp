

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.barisu.healthactivityapp.MessageRepository
import com.barisu.healthactivityapp.SocketForegroundService


class LoginViewModel() : ViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    init {
        // Observing messageReceived here
        MessageRepository.messageReceived.observeForever { receivedMessage ->
            Handler(Looper.getMainLooper()).post {
                // Update _loginSuccess LiveData upon receiving "connectionsuccess"
                _loginSuccess.value = receivedMessage == "connectionsuccess"
            }
        }
    }

    fun login(
        ipAddress: String,
        password: String,
        adminMode: Boolean,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val foregroundServiceIntent = Intent(context,SocketForegroundService::class.java)
        foregroundServiceIntent.action = SocketForegroundService.Actions.SEND_LOGIN_DATA.toString()
        foregroundServiceIntent.putExtra("IPADDRESS", ipAddress)
        foregroundServiceIntent.putExtra("PASSWORD", password)
        if (adminMode){
            foregroundServiceIntent.putExtra("USERTYPE", "admin")
        }
        else{
            foregroundServiceIntent.putExtra("USERTYPE", "user")
        }
        ContextCompat.startForegroundService(context, foregroundServiceIntent)

        loginSuccess.observeForever { success ->
            if (success) {
                onSuccess.invoke()
            }
        }

    }
}

