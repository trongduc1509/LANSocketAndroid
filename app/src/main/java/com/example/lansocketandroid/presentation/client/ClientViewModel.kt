package com.example.lansocketandroid.presentation.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lansocketandroid.domain.repository.CommunicatorControlRepository
import com.example.lansocketandroid.domain.repository.CommunicatorSendingRepository
import com.example.lansocketandroid.domain.repository.CommunicatorStreamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientViewModel(
    private val controlRepository: CommunicatorControlRepository,
    private val sendingRepository: CommunicatorSendingRepository,
    private val streamRepository: CommunicatorStreamRepository,
) : ViewModel() {
    private val _messages = MutableLiveData<List<String>>(mutableListOf());
    val messages: LiveData<List<String>> = _messages;

    fun connectToServer(ipAddress: String, port: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            controlRepository.startConnection(ipAddress, port)
            startReceivingAndObserving()
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendingRepository.sendMessage(message);
        }
    }

    private fun startReceivingAndObserving() {
        subscribeToCommunicator()
        startReceivingMessages()
    }

    private fun stopReceivingAndObserving() {
        stopReceivingMessages()
        unsubscribeToCommunicator()
    }

    private fun startReceivingMessages() {
        streamRepository.startReceiving()
    }

    private fun stopReceivingMessages() {
        streamRepository.stopReceiving()
    }

    private fun subscribeToCommunicator() {
        streamRepository.subscribe{
            viewModelScope.launch(Dispatchers.Main) {
                updateMessagesView(it.content);
            }
        };
    }

    private fun unsubscribeToCommunicator() {
        streamRepository.unsubscribe();
    }

    fun disconnect() {
        viewModelScope.launch(Dispatchers.IO) {
            stopReceivingAndObserving()
            controlRepository.stopConnection();
        }
    }

    private fun updateMessagesView(message: String) {
        val currentMessages = _messages.value?.toMutableList() ?: mutableListOf();
        currentMessages.add(message);
        _messages.postValue(currentMessages);
    }

    override fun onCleared() {
        disconnect()
        super.onCleared()
    }
}