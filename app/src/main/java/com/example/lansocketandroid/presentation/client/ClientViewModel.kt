package com.example.lansocketandroid.presentation.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lansocketandroid.app.ClientCommunicationListener
import com.example.lansocketandroid.app.ClientCommunicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientViewModel(
    private val clientCommunicator: ClientCommunicator,
) : ViewModel() {
    private val _messages = MutableLiveData<List<String>>(mutableListOf());
    val messages: LiveData<List<String>> = _messages;

    private val communicationListener =
        ClientCommunicationListener { message -> addMessages(message) }

    fun subscribeToCommunicator() {
        clientCommunicator.subscribeListener(communicationListener);
    }

    fun connectToServer(ipAddress: String, port: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            clientCommunicator.connect(ipAddress, port);
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            clientCommunicator.sendMessage(message);
        }
    }

    private fun addMessages(message: String) {
        val currentMessages = _messages.value?.toMutableList() ?: mutableListOf();
        currentMessages.add(message);
        _messages.postValue(currentMessages);
    }
}