package com.uhc.domain.chatbot

import com.uhc.api.chatbot.ChatbotRepository
import com.uhc.domain.chatbot.utils.cleanMarkdown

class SendMessageUseCase(
    private val repository: ChatbotRepository
) {
    suspend operator fun invoke(message: String): String =
        repository
            .sendMessage(message)
            .cleanMarkdown()
}
