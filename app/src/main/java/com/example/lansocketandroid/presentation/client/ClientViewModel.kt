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

    private val _isConnected = MutableLiveData<Boolean>(false);
    val isConnected: LiveData<Boolean> = _isConnected;

    private val _error = MutableLiveData<String?>(null);
    val error: LiveData<String?> = _error;

    private val communicationListener = object : ClientCommunicationListener {
        override fun onConnected() {
            _isConnected.postValue(true);
        }

        override fun onReceivedMessage(message: String) {
            addMessages(message);
        }

        override fun onDisconnected() {
            _isConnected.postValue(false);
        }

        override fun onError(error: String?) {
            _error.postValue(error);
        }
    }

    fun subscribeToCommunicator() {
        clientCommunicator.subscribeListener(communicationListener);
    }

    private fun unsubscribeFromCommunicator() {
        clientCommunicator.unsubscribeListener(communicationListener);
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

    fun disconnect() {
        viewModelScope.launch(Dispatchers.IO) {
            clientCommunicator.disconnect();
        }
    }

    private fun addMessages(message: String) {
        val currentMessages = _messages.value?.toMutableList() ?: mutableListOf();
        currentMessages.add(message);
        _messages.postValue(currentMessages);
    }

    override fun onCleared() {
        disconnect()
        unsubscribeFromCommunicator()
        super.onCleared()
    }
}