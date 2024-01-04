package com.barisu.healthactivityapp
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

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
        }
        isRunning = true
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
        val notification = NotificationCompat.Builder(this,"socket_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Active")
            .setContentText("Socket is running")
            .build()//he had "running_channel"
        startForeground(1,notification)
    }

    enum class Actions{
        START,STOP
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
