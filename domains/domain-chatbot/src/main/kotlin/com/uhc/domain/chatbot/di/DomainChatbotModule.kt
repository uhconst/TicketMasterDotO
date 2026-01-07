package com.uhc.domain.chatbot.di

import com.uhc.api.chatbot.di.apiChatbotModule
import com.uhc.domain.chatbot.GetApiKeyUseCase
import com.uhc.domain.chatbot.SaveApiKeyUseCase
import com.uhc.domain.chatbot.SendMessageUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainChatbotModule = module {
    includes(
        apiChatbotModule
    )
    factoryOf(::SendMessageUseCase)
    factoryOf(::GetApiKeyUseCase)
    factoryOf(::SaveApiKeyUseCase)
}