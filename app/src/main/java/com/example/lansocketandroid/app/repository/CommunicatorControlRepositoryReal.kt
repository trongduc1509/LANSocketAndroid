package com.example.lansocketandroid.app.repository

import com.example.lansocketandroid.data.remote.service.CommunicatorControlService
import com.example.lansocketandroid.domain.repository.CommunicatorControlRepository

class CommunicatorControlRepositoryReal(
    private val controlService: CommunicatorControlService
) : CommunicatorControlRepository {
    override fun startConnection(host: String, port: Int) {
        controlService.startConnection(host, port)
    }

    override fun stopConnection() {
        controlService.stopConnection()
    }
}