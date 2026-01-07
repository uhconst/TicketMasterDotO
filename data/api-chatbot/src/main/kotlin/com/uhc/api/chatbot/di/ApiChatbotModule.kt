package com.uhc.api.chatbot.di

import com.uhc.api.chatbot.ChatbotConfigLoader
import com.uhc.api.chatbot.ChatbotDataStore
import com.uhc.api.chatbot.ChatbotRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val apiChatbotModule = module {
    single { ChatbotDataStore(androidContext()) }
    single { ChatbotConfigLoader(androidContext()) }
    single { get<ChatbotConfigLoader>().loadConfig() }
    single { ChatbotRepository(get(), get()) }
}
