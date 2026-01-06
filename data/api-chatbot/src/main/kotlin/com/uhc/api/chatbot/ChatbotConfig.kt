package com.uhc.api.chatbot

import android.content.Context

// todo move key to backend
data class ChatbotConfig(
    val apiKey: String = "AIzaSyClA5Rbco9zqU9VsYNLYpve12Ph2z-LGCY",
    val modelName: String = "gemini-2.5-flash-lite",
    val systemPrompt: String
)

class ChatbotConfigLoader(private val context: Context) {
    fun loadConfig(): ChatbotConfig {
        val systemPrompt = context
            .assets
            .open("about_ury.txt")
            .bufferedReader()
            .use { it.readText() }
        return ChatbotConfig(systemPrompt = systemPrompt)
    }
}
