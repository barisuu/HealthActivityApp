
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisu.healthactivityapp.ClientClass
import com.barisu.healthactivityapp.HealthApp
import com.barisu.healthactivityapp.SocketConnection
import kotlinx.coroutines.launch


class LoginViewModel() : ViewModel() {

    //TODO move the socket connection from loginviewmodel to main activity
    //private val socketConnection = SocketConnection()
    private var messageReceived = ""


    fun login(
        ipAddress: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Try to connect to the server, and send the password send the password to verify.

                val socketConnection = SocketConnection()
                socketConnection.connect(ipAddress, 8888)
                socketConnection.send(password)
                messageReceived = socketConnection.receive()
                if(messageReceived == "Success"){
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

