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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.uhc.feature.chatbot.state.ChatbotUiState
import com.uhc.feature.chatbot.state.MessageState
import com.uhc.lib.compose.utils.R
import com.uhc.lib.compose.utils.annotations.TicketMasterPreview
import com.uhc.lib.compose.utils.theme.dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatbotLayout() {
    val viewModel: ChatbotViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    ChatbotScreen(
        uiState = uiState,
        onSettingsClicked = { viewModel.onSettingsClicked() },
        onDismissDialog = { viewModel.onDismissDialog() },
        onSaveApiKey = { viewModel.saveApiKey(it) },
        onSendMessage = { viewModel.sendMessage(it) }
    )
}

@Composable
internal fun ChatbotScreen(
    uiState: ChatbotUiState,
    onSettingsClicked: () -> Unit,
    onDismissDialog: () -> Unit,
    onSaveApiKey: (String) -> Unit,
    onSendMessage: (String) -> Unit
) {
    if (uiState.showDialog) {
        ChatbotApiKeyDialog(
            currentApiKey = uiState.apiKey ?: "",
            onDismissRequest = onDismissDialog,
            onConfirm = onSaveApiKey
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimensions.spacing.medium)
    ) {
        ChatbotHeader(onSettingsClicked = onSettingsClicked)

        val listState = rememberLazyListState()

        LaunchedEffect(uiState.messages.size) {
            if (uiState.messages.isNotEmpty()) {
                listState.animateScrollToItem(uiState.messages.size - 1)
            }
        }

        MessageList(
            messages = uiState.messages,
            modifier = Modifier.weight(1f),
            listState = listState
        )

        MessageInput(
            hasApiKey = uiState.hasApiKey,
            onSendMessage = onSendMessage,
            onSettingsClicked = onSettingsClicked
        )
    }
}

@Composable
internal fun ChatbotHeader(onSettingsClicked: () -> Unit) {
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
        IconButton(
            onClick = onSettingsClicked,
            modifier = Modifier.testTag("settings_button")
        ) {
            Icon(
                painter = painterResource(id = R.drawable.settings_24px),
                contentDescription = "Settings"
            )
        }
    }
}

@Composable
internal fun ChatbotApiKeyDialog(
    currentApiKey: String,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var apiKeyInput by remember { mutableStateOf("") }

    LaunchedEffect(currentApiKey) {
        apiKeyInput = currentApiKey
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.testTag("api_key_dialog"),
        title = { Text("Enter Gemini API Key") },
        text = {
            Column {
                Text("To use the chatbot, please provide a Gemini API Key.")
                Spacer(modifier = Modifier.padding(MaterialTheme.dimensions.spacing.small))
                OutlinedTextField(
                    value = apiKeyInput,
                    onValueChange = { apiKeyInput = it },
                    label = { Text("API Key") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("api_key_input"),
                    maxLines = 2
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(apiKeyInput) },
                modifier = Modifier.testTag("api_key_submit")
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.testTag("api_key_cancel")
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
internal fun MessageList(
    messages: List<MessageState>,
    modifier: Modifier = Modifier,
    listState: androidx.compose.foundation.lazy.LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .testTag("message_list")
            .padding(bottom = MaterialTheme.dimensions.spacing.small),
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(vertical = MaterialTheme.dimensions.spacing.small)
    ) {
        items(
            items = messages,
            key = { it.hashCode() }
        ) { msg ->
            MessageItem(msg)
        }
    }
}

@Composable
internal fun MessageItem(msg: MessageState) {
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
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.testTag(if (isUser) "user_message" else "bot_message")
        ) {
            Text(
                text = msg.text,
                color = textColor,
                modifier = Modifier.padding(MaterialTheme.dimensions.spacing.small + MaterialTheme.dimensions.spacing.xSmall)
            )
        }
    }
}

@Composable
internal fun MessageInput(
    hasApiKey: Boolean,
    onSendMessage: (String) -> Unit,
    onSettingsClicked: () -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    if (hasApiKey) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.testTag("message_input_area")
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(1f)
                    .testTag("message_input_field"),
                placeholder = { Text("Ask something about Ury...") }
            )
            Spacer(Modifier.width(MaterialTheme.dimensions.spacing.small))
            Button(
                onClick = {
                    val content = text.text.trim()
                    if (content.isNotEmpty()) {
                        onSendMessage(content)
                        text = TextFieldValue("")
                    }
                },
                modifier = Modifier.testTag("send_button")
            ) {
                Text("Send")
            }
        }
    } else {
        Button(
            onClick = onSettingsClicked,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("enter_api_key_button")
        ) {
            Text("Enter API Key")
        }
    }
}

@TicketMasterPreview
@Composable
private fun ChatbotScreenPreview() {
    ChatbotScreen(
        uiState = ChatbotUiState(
            messages = listOf(
                MessageState.User("Hello!"),
                MessageState.Bot("Hi there! How can I help you today?")
            ),
            apiKey = "fake-api-key"
        ),
        onSettingsClicked = {},
        onDismissDialog = {},
        onSaveApiKey = {},
        onSendMessage = {}
    )
}

@TicketMasterPreview
@Composable
private fun ChatbotScreenNoApiKeyPreview() {
    ChatbotScreen(
        uiState = ChatbotUiState(
            messages = emptyList(),
            apiKey = null
        ),
        onSettingsClicked = {},
        onDismissDialog = {},
        onSaveApiKey = {},
        onSendMessage = {}
    )
}

@TicketMasterPreview
@Composable
private fun ChatbotApiKeyDialogPreview() {
    ChatbotApiKeyDialog(
        currentApiKey = "existing-api-key",
        onDismissRequest = {},
        onConfirm = {}
    )
}