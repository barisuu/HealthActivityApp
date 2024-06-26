package com.barisu.healthactivityapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object MessageRepository {
    private val _messageReceived = MutableLiveData<String>()
    val messageReceived: LiveData<String>
        get() = _messageReceived

    private lateinit var recentActivityViewModel: RecentActivityViewModel

    fun setViewModel(viewModel: RecentActivityViewModel) {
        recentActivityViewModel = viewModel
    }
    fun processReceivedData(data: String) {
        println("Processing received data : $data")
        _messageReceived.postValue(data)


    }
}
