package com.example.lansocketandroid.data.remote.service

import com.example.lansocketandroid.utils.stream.StreamMessageReceiver

interface CommunicatorStreamService {
    fun startReceiving()

    fun stopReceiving()

    fun addReceiver(receiver: StreamMessageReceiver)

    fun removeReceiver(receiver: StreamMessageReceiver)
}