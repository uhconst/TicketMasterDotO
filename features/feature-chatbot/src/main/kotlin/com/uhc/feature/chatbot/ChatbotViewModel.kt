package com.uhc.feature.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.domain.chatbot.GetApiKeyUseCase
import com.uhc.domain.chatbot.SaveApiKeyUseCase
import com.uhc.domain.chatbot.SendMessageUseCase
import com.uhc.feature.chatbot.state.ChatbotUiState
import com.uhc.feature.chatbot.state.MessageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatbotViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val saveApiKeyUseCase: SaveApiKeyUseCase,
    getApiKeyUseCase: GetApiKeyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatbotUiState())
    val uiState: StateFlow<ChatbotUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getApiKeyUseCase()
                .collect { apiKey ->
                    _uiState.update { it.copy(apiKey = apiKey) }
                    if (apiKey == null) {
                        _uiState.update { it.copy(showDialog = true) }
                    }
                }
        }
    }

    fun onSettingsClicked() {
        _uiState.update { it.copy(showDialog = true) }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(showDialog = false) }
    }

    fun saveApiKey(key: String) {
        viewModelScope.launch {
            saveApiKeyUseCase(key)
            onDismissDialog()
        }
    }

    fun sendMessage(text: String) {
        _uiState.update { it.copy(messages = it.messages + MessageState.User(text)) }

        viewModelScope.launch {
            val reply = sendMessageUseCase(text)
            _uiState.update { it.copy(messages = it.messages + MessageState.Bot(reply)) }
        }
    }
}
