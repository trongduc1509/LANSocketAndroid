package com.example.lansocketandroid.data.remote.service

interface CommunicatorControlService {
    fun startConnection(host: String, port: Int)

    fun stopConnection()
}