package com.example.lansocketandroid.app.service

import com.example.lansocketandroid.data.remote.dto.CommunicatingMessageDTO
import com.example.lansocketandroid.data.remote.service.CommunicatorSendingService
import com.example.lansocketandroid.platform.communicator.CommunicatorControl
import com.example.lansocketandroid.utils.byte.GsonByteUtils

class CommunicatorSendingServiceReal(
    private val communicatorControl: CommunicatorControl,
) : CommunicatorSendingService {
    override fun sendMessage(message: String) {
        communicatorControl.send(
            GsonByteUtils.toByteArray(
                CommunicatingMessageDTO(
                    sender = "CLIENT",
                    content = message,
                )
            )
        )
    }
}