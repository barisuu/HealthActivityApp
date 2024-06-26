package com.barisu.healthactivityapp
import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SocketForegroundService : Service() {
    private val channelId = "SocketChannelId"
    private val channelName = "Socket Channel"
    private var isRunning = false


    // Socket connection
    private val socketConnection = SocketConnection()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
            Actions.SEND_LOGIN_DATA.toString() -> {
                val ipAddress = intent.getStringExtra("IPADDRESS")
                val password = intent.getStringExtra("PASSWORD")
                val userType = intent.getStringExtra("USERTYPE")
                if (ipAddress != null && password != null && userType != null){
                    loginUser(ipAddress,password,userType)
                }
            }
            Actions.SEND_DATA.toString() ->{
                val data = intent.getStringExtra("DATA")
                if(data != null){
                    sendData(data)
                }
            }
        }
        isRunning = true
        return super.onStartCommand(intent, flags, startId)
    }
    enum class Actions{
        START,SEND_LOGIN_DATA,SEND_DATA,STOP
    }

    private fun start(){
        val notification = NotificationCompat.Builder(this,"socket_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Active")
            .setContentText("Socket is running")
            .build()
        startForeground(1,notification)
    }



    fun loginUser(ipAddress: String, password: String, userType: String){
        val loginData = "${userType}-${password}"

        connectToServer(ipAddress,8888)
        sendData(loginData)
        startListening()
    }

    fun startListening(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val receiveChannel = socketConnection.receive()
                for (receivedData in receiveChannel) {
                    println("Received data in service is: $receivedData")
                    if(receivedData == "ANOMALY"){
                        handleAnomaly()
                    }
                    else{
                        MessageRepository.processReceivedData(receivedData)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle exceptions or connection errors
            }
        }
    }
    fun connectToServer(address: String, port: Int){
        socketConnection.connect(address,port)
    }

    fun disconnectFromServer(){
        socketConnection.disconnect()
    }

    fun sendData(data: String){
        CoroutineScope(Dispatchers.IO).launch {
            delay(100)
            socketConnection.send(data)
        }
    }

    fun handleAnomaly(){
        val notification = NotificationCompat.Builder(this,"socket_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Anomaly")
            .setContentText("An anomaly has been detected!")
            .setSmallIcon(R.drawable.ic_anomaly_warning)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        with(NotificationManagerCompat.from(this)){
            if (ActivityCompat.checkSelfPermission(
                    this@SocketForegroundService,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(2,notification)
        }
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