package com.uhc.feature.chatbot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.uhc.feature.chatbot.state.MessageState
import com.uhc.lib.compose.utils.R
import com.uhc.lib.compose.utils.theme.dimensions
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatbotLayout() {
    val viewModel: ChatbotViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsState()
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()

    var apiKeyInput by remember { mutableStateOf("") }

    LaunchedEffect(uiState.showDialog) {
        if (uiState.showDialog) {
            apiKeyInput = uiState.apiKey ?: ""
        }
    }

    if (uiState.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissDialog() },
            title = { Text("Enter Gemini API Key") },
            text = {
                Column {
                    Text("To use the chatbot, please provide a Gemini API Key.")
                    Spacer(modifier = Modifier.padding(MaterialTheme.dimensions.spacing.small))
                    OutlinedTextField(
                        value = apiKeyInput,
                        onValueChange = { apiKeyInput = it },
                        label = { Text("API Key") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.saveApiKey(apiKeyInput) }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onDismissDialog() }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimensions.spacing.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimensions.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "💬 Chat with UryBot",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = { viewModel.onSettingsClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.settings_24px),
                    contentDescription = "Settings"
                )
            }
        }

        val listState = rememberLazyListState()

        LaunchedEffect(uiState.messages.size) {
            if (uiState.messages.isNotEmpty()) {
                listState.animateScrollToItem(uiState.messages.size - 1)
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimensions.spacing.small),
            verticalArrangement = Arrangement.Top,
            contentPadding = PaddingValues(vertical = MaterialTheme.dimensions.spacing.small)
        ) {
            items(
                items = uiState.messages,
                key = { it.hashCode() }
            ) { msg ->
                val isUser = msg is MessageState.User
                val bgColor =
                    if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                val textColor =
                    if (isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MaterialTheme.dimensions.spacing.xSmall),
                    contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Surface(
                        color = bgColor,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = msg.text,
                            color = textColor,
                            modifier = Modifier.padding(MaterialTheme.dimensions.spacing.small + MaterialTheme.dimensions.spacing.xSmall)
                        )
                    }
                }
            }
        }

        if (uiState.hasApiKey) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Ask something about Ury...") }
                )
                Spacer(Modifier.width(MaterialTheme.dimensions.spacing.small))
                Button(
                    onClick = {
                        val content = text.text.trim()
                        if (content.isNotEmpty()) {
                            scope.launch {
                                viewModel.sendMessage(content)
                                text = TextFieldValue("")
                            }
                        }
                    }
                ) {
                    Text("Send")
                }
            }
        } else {
            Button(
                onClick = { viewModel.onSettingsClicked() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enter API Key")
            }
        }
    }
}