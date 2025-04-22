package com.example.lansocketandroid.domain.repository

interface CommunicatorControlRepository {
    fun startConnection(host: String, port: Int)

    fun stopConnection()
}