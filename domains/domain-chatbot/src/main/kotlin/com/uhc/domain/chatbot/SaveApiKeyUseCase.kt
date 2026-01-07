package com.uhc.domain.chatbot

import com.uhc.api.chatbot.ChatbotDataStore

class SaveApiKeyUseCase(
    private val dataStore: ChatbotDataStore
) {
    suspend operator fun invoke(key: String) = dataStore.saveApiKey(key)
}
