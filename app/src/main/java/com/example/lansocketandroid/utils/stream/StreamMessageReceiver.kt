package com.example.lansocketandroid.utils.stream

abstract class StreamMessageReceiver {
    abstract fun onReceiveBytes(bytes: ByteArray)
}