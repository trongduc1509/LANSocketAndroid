package com.example.lansocketandroid.app.service

import com.example.lansocketandroid.data.remote.service.CommunicatorStreamService
import com.example.lansocketandroid.platform.communicator.CommunicatorControl
import com.example.lansocketandroid.platform.communicator.CommunicatorMessageListener
import com.example.lansocketandroid.utils.stream.StreamMessageReceiver

class CommunicatorStreamServiceReal(
    private val communicatorControl: CommunicatorControl
) : CommunicatorStreamService {
    private val receivers = mutableSetOf<StreamMessageReceiver>()

    init {
        communicatorControl.subscribeMessage(listener = object: CommunicatorMessageListener {
            override fun onReceive(bytes: ByteArray) {
                onReceivingBytes(bytes)
            }
        })
    }

    override fun startReceiving() {
        communicatorControl.startReceiving()
    }

    override fun stopReceiving() {
        communicatorControl.stopReceiving()
    }

    private fun onReceivingBytes(bytes: ByteArray) {
        receivers.forEach {
            it.onReceiveBytes(bytes)
        }
    }

    override fun addReceiver(receiver: StreamMessageReceiver) {
        receivers.add(receiver)
    }

    override fun removeReceiver(receiver: StreamMessageReceiver) {
        receivers.remove(receiver)
    }
}