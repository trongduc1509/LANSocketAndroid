package com.example.lansocketandroid.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lansocketandroid.core.sockets.SocketType


@Composable
fun SocketSelectionUI(
    modifier: Modifier = Modifier,
    onSelectSocketType: (SocketType) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Select your Socket Type",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                onSelectSocketType.invoke(SocketType.server)
            }
        ) {
            Text(text = "Server", maxLines = 1)
        }
        Button(
            onClick = {
                onSelectSocketType.invoke(SocketType.client)
            }
        ) {
            Text(text = "Client", maxLines = 2)
        }
    }
}