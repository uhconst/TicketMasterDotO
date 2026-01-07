package com.uhc.domain.chatbot

import com.uhc.api.chatbot.ChatbotDataStore
import kotlinx.coroutines.flow.Flow

class GetApiKeyUseCase(
    private val dataStore: ChatbotDataStore
) {
    operator fun invoke(): Flow<String?> = dataStore.getApiKey
}