package com.uhc.feature.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhc.domain.chatbot.SendMessageUseCase
import com.uhc.feature.chatbot.state.MessageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatbotViewModel(
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _messagesState = MutableStateFlow<List<MessageState>>(emptyList())
    val messagesState: StateFlow<List<MessageState>> = _messagesState.asStateFlow()

    fun sendMessage(text: String) {
        _messagesState.value += MessageState.User(text)

        viewModelScope.launch {
            val reply = sendMessageUseCase(text)
            _messagesState.value += MessageState.Bot(reply)
        }
    }
}
