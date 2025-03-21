package com.example.lansocketandroid

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lansocketandroid.presentation.client.ClientViewModel
import com.example.lansocketandroid.presentation.widget.ChatAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClientActivity : ComponentActivity() {

    private val viewModel by viewModel<ClientViewModel>()

    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.subscribeToCommunicator()

        setUpUIs()
    }

    private fun setUpUIs() {
        enableEdgeToEdge()
        setContentView(R.layout.client_ui)
        applyInsets()

        val etServerIP = findViewById<EditText>(R.id.etServerIP)
        val etPort = findViewById<EditText>(R.id.etPort)
        val btnConnect = findViewById<Button>(R.id.btnConnect)
        val rvMessages = findViewById<RecyclerView>(R.id.rvMessages)
        val etMessage = findViewById<EditText>(R.id.etMessage)
        val btnSend = findViewById<Button>(R.id.btnSend)

        rvMessages.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        chatAdapter = ChatAdapter()
        rvMessages.adapter = chatAdapter

        viewModel.messages.observe(this) { messages ->
            chatAdapter.updateMessages(messages)
            rvMessages.scrollToPosition(messages.size - 1)
        }

        viewModel.isConnected.observe(this) { isConnected ->
            etServerIP.isEnabled = !isConnected
            etPort.isEnabled = !isConnected
            btnSend.isEnabled = isConnected
            btnConnect.text = if (isConnected) "Disconnect from server" else "Connect to server"
            btnConnect.setOnClickListener {
                if (isConnected) {
                    viewModel.disconnect()
                } else {
                    val ip = etServerIP.text.toString()
                    val port = etPort.text.toString().toIntOrNull()
                    if (ip.isNotEmpty() && port != null) {
                        viewModel.connectToServer(ip, port)
                    }
                }
            }
        }

        btnSend.setOnClickListener {
            val message = etMessage.text.toString()
            if (message.isNotEmpty()) {
                viewModel.sendMessage(message)
                etMessage.text.clear()
            }
        }
    }

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom + imeHeight)
            insets
        }
    }
}