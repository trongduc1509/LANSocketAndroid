package com.example.lansocketandroid.app.repository

import com.example.lansocketandroid.data.remote.dto.CommunicatingMessageDTO
import com.example.lansocketandroid.data.remote.service.CommunicatorStreamService
import com.example.lansocketandroid.domain.entity.MessageEntity
import com.example.lansocketandroid.domain.repository.CommunicatorStreamRepository
import com.example.lansocketandroid.domain.toEntity
import com.example.lansocketandroid.utils.stream.receiver.ClientStreamReceiver

class CommunicatorStreamRepositoryReal(
    private val streamService: CommunicatorStreamService,
) : CommunicatorStreamRepository {
    private var receiver: ClientStreamReceiver? = null

    override fun startReceiving() {
        streamService.startReceiving()
    }

    override fun stopReceiving() {
        streamService.stopReceiving()
    }

    override fun subscribe(resultHandler: (MessageEntity) -> Unit) {
        if (receiver != null) return

        receiver = object: ClientStreamReceiver() {
            override fun onReceiveConversationEvent(event: CommunicatingMessageDTO) {
                resultHandler(event.toEntity())
            }
        }

        streamService.addReceiver(receiver!!)
    }

    override fun unsubscribe() {
        if (receiver == null) return
        streamService.removeReceiver(receiver!!)
        receiver = null
    }
}