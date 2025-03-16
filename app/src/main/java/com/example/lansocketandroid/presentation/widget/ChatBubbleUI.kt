package com.example.lansocketandroid.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChatBubbleUI(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                if (message
                        .lowercase()
                        .startsWith("Client:")
                ) Color.Blue else Color.Gray,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(8.dp)
    ) {
        Text(
            text = message,
            color = Color.White,
        )
    }
}