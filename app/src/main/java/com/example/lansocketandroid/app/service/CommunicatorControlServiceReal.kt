package com.example.lansocketandroid.app.service

import com.example.lansocketandroid.data.remote.service.CommunicatorControlService
import com.example.lansocketandroid.platform.communicator.CommunicatorControl

class CommunicatorControlServiceReal(
    private val communicatorControl: CommunicatorControl,
) : CommunicatorControlService {
    override fun startConnection(host: String, port: Int) {
        communicatorControl.connect(host, port)
    }

    override fun stopConnection() {
        communicatorControl.close()
    }
}