package com.uhc.api.chatbot

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.first

class ChatbotRepository(
    private val config: ChatbotConfig,
    private val dataStore: ChatbotDataStore
) {

    private var _model: GenerativeModel? = null

    private suspend fun getModel(): GenerativeModel {
        val currentModel = _model
        if (currentModel != null) return currentModel

        val apiKey = dataStore.getApiKey.first()
            ?: throw IllegalStateException("API Key not found")

        return GenerativeModel(
            modelName = config.modelName,
            apiKey = apiKey
        ).also { _model = it }
    }

    private var _chat: Chat? = null

    private suspend fun getChat(): Chat {
        val currentChat = _chat
        if (currentChat != null) return currentChat

        val model = getModel()
        return model.startChat(
            history = listOf(
                content(role = "user", init = { text(config.systemPrompt) })
            )
        ).also { _chat = it }
    }

    suspend fun sendMessage(message: String): String =
        getChat().sendMessage(message).text ?: "Sorry, I couldn't come up with a reply."
}
