package com.example.lansocketandroid.presentation.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lansocketandroid.presentation.widget.ChatBubbleUI

@Composable
fun ClientScreen(
    viewModel: ClientViewModel,
    modifier: Modifier = Modifier,
) {
    var serverIP by remember { mutableStateOf("") }
    var portNum: Int? by remember { mutableStateOf(null) }

    val messages by viewModel.messages.observeAsState(mutableListOf())

    var inputMessage: String by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = messages) {
        if (messages.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .imePadding(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "Server IP", maxLines = 1)
            TextField(
                value = serverIP,
                placeholder = {
                    Text(text = "Input Server's IP")
                },
                onValueChange = { value -> serverIP = value }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "Port Number", maxLines = 1)
            TextField(
                value = if (portNum != null) portNum.toString() else "",
                placeholder = {
                    Text(text = "Input Port Number")
                },
                onValueChange = { value -> portNum = value.toInt() },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.align(alignment = Alignment.End),
            onClick = {
                if (serverIP.isNotEmpty() && portNum != null) {
                    viewModel.connectToServer(serverIP, portNum!!)
                }
            }
        ) {
            Text("Connect to server")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            LazyColumn(
                state = listState,
                reverseLayout = true,
                modifier = Modifier.fillMaxSize(),
            ) {
                items(messages.reversed()) { message ->
                    ChatBubbleUI(message = message)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = inputMessage,
                onValueChange = { inputMessage = it },
                label = { Text("Enter message") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {})
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (inputMessage.isNotEmpty()) {
                        viewModel.sendMessage(inputMessage);
                        inputMessage = "";
                    }
                },
            ) {
                Text("Send")
            }

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}