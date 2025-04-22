package com.example.lansocketandroid.domain

import com.example.lansocketandroid.data.remote.dto.CommunicatingMessageDTO
import com.example.lansocketandroid.domain.entity.MessageEntity

fun CommunicatingMessageDTO.toEntity(): MessageEntity {
    return MessageEntity(content = "$sender: $content")
}