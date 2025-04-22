package com.example.lansocketandroid.app.repository

import com.example.lansocketandroid.data.remote.service.CommunicatorSendingService
import com.example.lansocketandroid.domain.repository.CommunicatorSendingRepository

class CommunicatorSendingRepositoryReal(
    private val sendingService: CommunicatorSendingService,
) : CommunicatorSendingRepository {
    override fun sendMessage(message: String) {
        sendingService.sendMessage(message)
    }
}