package com.uhc.api.chatbot

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class ChatbotRepository(
    private val config: ChatbotConfig
) {

    private val model by lazy {
        GenerativeModel(
            modelName = config.modelName,
            apiKey = config.apiKey
        )
    }

    private val chat by lazy {
        model.startChat(
            history = listOf(
                content(role = "user", init = { text(config.systemPrompt) })
            )
        )
    }

    suspend fun sendMessage(message: String): String =
        chat.sendMessage(message).text ?: "Sorry, I couldn't come up with a reply."
}
