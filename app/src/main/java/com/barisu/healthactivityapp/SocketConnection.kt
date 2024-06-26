package com.barisu.healthactivityapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

// Implementation of sockets to establish connection.

class SocketConnection() : ConnectionInterface {

    private val receiveChannel = Channel<String>()

    private lateinit var receiveJob: Job
    private lateinit var client : Socket
    private lateinit var writer : OutputStreamWriter
    private lateinit var reader : BufferedReader
    // Function to connect to a socket server.
    // Connects to a socket server with port 8888 for demonstration purposes.
    // Generates the writer and reader to send and receive data.
    override fun connect(address: String, port: Int) {
        Thread{
            print("Login data is being sent to the server.")
            client = Socket(address,8888)
            writer = OutputStreamWriter(client.getOutputStream())
            reader = BufferedReader(InputStreamReader(client.getInputStream()))

            startReceiving()
        }.start()

    }

    // Function to close the socket. TODO communication with the server before disconnecting
    override fun disconnect() {
        try{
            client.close()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    // Function to send data to the server.
    // Uses withContext in order to get the outside context of the block.
    override suspend fun send(data: String) {
        withContext(Dispatchers.IO){
            try {
                writer.write(data)
                writer.flush()
            } catch (e: Exception) {
                throw e
            }
        }
    }
    override suspend fun receive(): Channel<String>{
        return receiveChannel
    }

    private fun startReceiving() {
        receiveJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                while (true) {
                    println("Reader listening again.")
                    val data = reader.readLine() ?: break
                    receiveChannel.send(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                receiveChannel.close()
            }
        }
    }

    fun parseMessage(receivedData : String) : String{
        var splitData = receivedData.split("-")
        return splitData[0]
    }
}
