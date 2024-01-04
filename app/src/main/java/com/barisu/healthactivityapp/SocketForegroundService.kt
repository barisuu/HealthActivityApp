package com.barisu.healthactivityapp
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
            .build()//he had "running_channel"
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
                    MessageRepository.processReceivedData(receivedData)
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
            socketConnection.send(data)
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
