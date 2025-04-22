package com.example.lansocketandroid.domain.repository

interface CommunicatorSendingRepository {
    fun sendMessage(message: String)
}