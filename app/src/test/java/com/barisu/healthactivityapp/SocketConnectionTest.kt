package com.barisu.healthactivityapp

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SocketConnectionTest {

    private lateinit var socketConnection: SocketConnection

    private val serverAddress = "192.168.137.26"
    private val serverPort = 8888

    //Creating socket connection class and connecting before testing.
    @Before
    fun setup() = runBlocking{
        socketConnection = SocketConnection()
        socketConnection.connect(serverAddress, serverPort)
        // As socketConnection has lateinit variables, need to add a delay
        // or sometimes initialization errors might occur
        delay(100)
    }

    //Testing send and login functionality by logging in successfuly.
    //Expected result is receiving connectionsuccess from the server.
    @Test
    fun sendLogin() = runBlocking {
        val testData = "admin-123"

        socketConnection.send(testData)
        // Verify that the "connectionsuccess" was sent by the server
        val receiveChannel = socketConnection.receive()
        val receivedData = receiveChannel.receive()


        assertEquals("connectionsuccess", receivedData)
        socketConnection.send("disconnect")
    }

    //Testing login functionality in the case of a wrong data sent to the server.
    @Test
    fun sendLoginFail() = runBlocking {
        val testData = "-123"

        socketConnection.send(testData)
        // Verify that the "connectionsuccess" was not sent by the server
        val receiveChannel = socketConnection.receive()
        val receivedData = receiveChannel.receive()

        assertEquals("connectionfailed", receivedData)
        socketConnection.send("disconnect")
    }

    //Testing last prediction graph by making a lastPred request from the server. The expected data is a mock array stored in the server.
    @Test
    fun receiveLastPred() = runBlocking {
        val expectedConnectionSuccess = "connectionsuccess"
        val expectedLastPred= "lastpred:[1.0, 3.0, 0.0, 2.0, 4.0, 0.0, 1.0, 3.0, 2.0, 4.0, 1.0, 0.0, 3.0, 2.0, 4.0, 1.0, 0.0, 2.0, 3.0, 4.0]"

        // Send login credentials

        socketConnection.send("admin-123")

        // Wait for a brief delay before sending the "lastPred" request
        delay(100)

        // Send the "lastPred" request
        socketConnection.send("lastPred")

        // Receive data from the server
        val receiveChannel = socketConnection.receive()

        // Verify the first received data is "connectionsuccess"
        val connectionSuccess = receiveChannel.receive()
        assertEquals(expectedConnectionSuccess, connectionSuccess)

        // Receive the rest of the data as the "lastPred" array
        val lastPredData = receiveChannel.receive()

        // Verify the "lastPred" array
        assertEquals(expectedLastPred, lastPredData)
        socketConnection.send("disconnect")
    }

    // Testing current activity by making a curAct request from the server. Expected data is one of the possible activities.
    @Test
    fun receiveCurAct() = runBlocking {
        val expectedConnectionSuccess = "connectionsuccess"
        val expectedActivities = listOf<String>("Unlabelled Activity", "Stand", "Walk", "Sit", "Lie")
        // Send login credentials

        socketConnection.send("admin-123")

        // Wait for a brief delay before sending the "curAct" request to let server process
        delay(100)

        // Send the "curAct" request
        socketConnection.send("curAct")
        socketConnection.send("nothing") // Sending nothing immediately so that server doesn't continuously send data.

        // Receive data from the server
        val receiveChannel = socketConnection.receive()

        // Verify the first received data is "connectionsuccess"
        val connectionSuccess = receiveChannel.receive()
        assertEquals(expectedConnectionSuccess, connectionSuccess)

        // Verify the second reeived data is "activity-percentage"
        // Since percentage is changing just checking if the activity is within the possible options.
        val curActData = receiveChannel.receive()
        val activity = curActData.split("-")[0].trim()
        print("Curact is : ${activity}")
        if (activity in expectedActivities){
            assert(true)
        }
        else{
            assert(false)
        }
        socketConnection.send("disconnect")
    }

}