import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.barisu.healthactivityapp.R
import com.barisu.healthactivityapp.SocketConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SocketForegroundService : Service() {
    private val channelId = "SocketChannelId"
    private val channelName = "Socket Channel"
    private var isRunning = false


    // Socket connection
    private val socketConnection = SocketConnection()

    override fun onCreate() {
        super.onCreate()
        println("Created service")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForegroundService()
        isRunning = true
        println("Started service")
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startForegroundService() {
        val channelId = "SocketChannelId"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Health Tracker")
            .setContentText("Socket Service is running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        // Add any additional configurations to the notification

        val notification = notificationBuilder.build()
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun connectToServer(address: String, port: Int){
        socketConnection.connect(address,port)
    }

    fun disconnectFromServer(){
        socketConnection.disconnect()
    }

    suspend fun sendData(data: String){
        socketConnection.send(data)
    }

    suspend fun receiveData() :String{
        return ""
    }


    override fun onDestroy() {
        // Disconnect and clean up socket connection when the service is destroyed
        socketConnection.disconnect()
        isRunning = false
        super.onDestroy()
    }

    fun isServiceRunning(): Boolean{
        return isRunning
    }
}
