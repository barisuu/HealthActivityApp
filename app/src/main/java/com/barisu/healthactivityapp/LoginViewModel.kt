

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisu.healthactivityapp.MessageRepository
import com.barisu.healthactivityapp.SocketConnection
import kotlinx.coroutines.launch


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
        socketConnection: SocketConnection,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Try to connect to the server, and send the password send the password to verify.
                socketConnection.connect(ipAddress, 8888)
                var data = ""
                if(adminMode){
                    data = "admin-${password}"
                    socketConnection.send(data)
                }
                else{
                    data = "user-${password}"
                    socketConnection.send(data)
                }
                socketConnection.receive()
            } catch (e: Exception) {
                onFailure.invoke()
            }
        }
        loginSuccess.observeForever { success ->
            if (success) {
                onSuccess.invoke()
            }
        }
    }
}

