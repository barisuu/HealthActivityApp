

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisu.healthactivityapp.MessageRepository
import com.barisu.healthactivityapp.SocketConnection
import kotlinx.coroutines.launch


class LoginViewModel() : ViewModel() {

    private var messageReceived = ""


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
                if(MessageRepository.messageReceived.value == "Success"){
                    onSuccess.invoke()
                }
                else{
                    throw Exception("Couldn't reach.")
                }
            } catch (e: Exception) {
                onFailure.invoke()
            }
        }
    }
}

