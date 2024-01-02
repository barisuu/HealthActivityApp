package com.barisu.healthactivityapp

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Executors

class ClientClass(hostAddress: String): Thread() {

    var hostAddress: String = hostAddress
    lateinit var inputStream: InputStream
    lateinit var outputStream: OutputStream
    lateinit var socket: Socket
    lateinit var writer : OutputStreamWriter

    fun write(msg: String){
        try {
            writer.write(msg)
            writer.flush()
        }catch (ex: IOException){
            ex.printStackTrace()
        }
    }

    override fun run() {
        val executor = Executors.newSingleThreadExecutor()
        var handler = Handler(Looper.getMainLooper())

        executor.execute(kotlinx.coroutines.Runnable {
            kotlin.run {
                try {
                    socket = Socket()
                    socket.connect(InetSocketAddress(InetAddress.getByName(hostAddress),8888),500)
                    writer = OutputStreamWriter(socket.getOutputStream())
                    inputStream = socket.getInputStream()
                    outputStream = socket.getOutputStream()
                }catch (ex:IOException){
                    ex.printStackTrace()
                }
                val buffer =ByteArray(1024)
                var byte:Int
                while (true){
                    try{
                        byte = inputStream.read(buffer)
                        if(byte>0){
                            val finalBytes = byte
                            handler.post(Runnable{
                                kotlin.run {
                                    val tmpMeassage = String(buffer,0,finalBytes)

                                    Log.i("client class", tmpMeassage)
                                }
                            })
                        }
                    }catch (ex:IOException){
                        ex.printStackTrace()
                    }
                }
            }
        })
    }

}