package com.uhc.api.chatbot.di

import com.uhc.api.chatbot.ChatbotConfig
import com.uhc.api.chatbot.ChatbotRepository
import org.koin.dsl.module

val apiChatbotModule = module {
    //todo
    single { ChatbotRepository(/*get<ChatbotConfig>()*/) }
}
