package com.example.lansocketandroid.utils.stream.receiver

import com.example.lansocketandroid.data.remote.dto.CommunicatingMessageDTO
import com.example.lansocketandroid.utils.byte.GsonByteUtils
import com.example.lansocketandroid.utils.stream.StreamMessageReceiver

abstract class ClientStreamReceiver : StreamMessageReceiver() {
    override fun onReceiveBytes(bytes: ByteArray) {
        try {
            onReceiveConversationEvent(
                GsonByteUtils.parse(bytes, CommunicatingMessageDTO::class.java)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun onReceiveConversationEvent(event: CommunicatingMessageDTO)
}