package com.barisu.healthactivityapp

import kotlinx.coroutines.channels.Channel


// Interface to handle connections. If another connection method will be implemented in the future (i.e WiFi Direct etc.) these methods must be implemented.
interface ConnectionInterface{
    fun connect(address: String, port: Int)
    fun disconnect()
    suspend fun send(data: String)
    suspend fun receive()
}