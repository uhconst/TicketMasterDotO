package com.uhc.feature.chatbot.state

data class ChatbotUiState(
    val messages: List<MessageState> = emptyList(),
    val apiKey: String? = null,
    val showDialog: Boolean = false
) {
    val hasApiKey: Boolean
        get() = !apiKey.isNullOrBlank()
}
