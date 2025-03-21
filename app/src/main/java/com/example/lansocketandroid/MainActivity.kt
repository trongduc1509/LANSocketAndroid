package com.example.lansocketandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.example.lansocketandroid.core.sockets.SocketType
import com.example.lansocketandroid.presentation.ui.SocketSelectionUI
import com.example.lansocketandroid.presentation.ui.theme.LANSocketAndroidTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LANSocketAndroidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "LAN SOCKET DEMO")
                            }
                        )
                    }
                ) { innerPadding ->
                    SocketSelectionUI(
                        modifier = Modifier.padding(innerPadding),
                        onSelectSocketType = {
                            onSelectSocketType(it)
                        }
                    )
                }
            }
        }
    }

    private fun onSelectSocketType(type: SocketType) {
        when(type) {
            SocketType.server -> startActivity(Intent(this, ServerActivity::class.java))
            SocketType.client -> startActivity(Intent(this, ClientActivity::class.java))
        }
    }
}
