package com.example.lansocketandroid.platform.communicator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

class ClientCommunicator : CommunicatorControl {
    private var socket: Socket? = null;
    private var inputStream: DataInputStream? = null;
    private var outputStream: DataOutputStream? = null;

    private var receivingJob: Job? = null;

    private val listeners = mutableSetOf<CommunicatorMessageListener>()

    private val isConnected = MutableLiveData(false)
    override val isConnectionLive: LiveData<Boolean> = isConnected

    override fun connect(host: String, port: Int) {
        socket = Socket(host, port)
        setupCommunicatorIO();
        updateConnectionStatus(socket?.isConnected ?: false);
    }

    override fun send(bytes: ByteArray) {
        if (outputStream == null) return
        outputStream?.writeInt(bytes.size)
        outputStream?.write(bytes)
        outputStream?.flush()
    }


    override fun startReceiving() {
        if (receivingJob != null) return

        receivingJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val input = inputStream ?: throw IOException("Input stream is null")

                while (isActive) {
                    try {
                        val length = input.readInt()
                        val buffer = ByteArray(length)
                        input.readFully(buffer)
                        onReceiveMessage(buffer)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                close()
            }
        }
    }

    override fun stopReceiving() {
        receivingJob?.cancel()
        receivingJob = null
    }

    override fun close() {
        try {
            stopReceiving()
            closeCommunicatorIO()
            socket?.close()
            socket = null
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            updateConnectionStatus(socket?.isConnected ?: false)
        }
    }

    override fun subscribeMessage(listener: CommunicatorMessageListener) {
        listeners.add(listener)
    }

    override fun unsubscribeMessage(listener: CommunicatorMessageListener) {
        listeners.remove(listener)
    }

    private fun onReceiveMessage(bytes: ByteArray) {
        listeners.forEach {
            it.onReceive(bytes)
        }
    }

    private fun updateConnectionStatus(isConnected: Boolean) {
        this.isConnected.postValue(isConnected)
    }

    private fun setupCommunicatorIO() {
        if (socket == null) return
        inputStream = DataInputStream(socket!!.getInputStream())
        outputStream = DataOutputStream(socket!!.getOutputStream())
    }

    private fun closeCommunicatorIO() {
        inputStream?.close()
        outputStream?.close()
    }
}

interface CommunicatorControl {
    val isConnectionLive: LiveData<Boolean>

    fun connect(host: String, port: Int)

    fun close()

    fun send(bytes: ByteArray)

    fun startReceiving()

    fun stopReceiving()

    fun subscribeMessage(listener: CommunicatorMessageListener)

    fun unsubscribeMessage(listener: CommunicatorMessageListener)
}

interface CommunicatorMessageListener {
    fun onReceive(bytes: ByteArray)
}