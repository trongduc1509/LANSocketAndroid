package com.example.lansocketandroid.domain.repository

import com.example.lansocketandroid.domain.entity.MessageEntity

interface CommunicatorStreamRepository {
    fun startReceiving()

    fun stopReceiving()

    fun subscribe(resultHandler: (MessageEntity) -> Unit)

    fun unsubscribe()
}