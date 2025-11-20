package com.uhc.feature.chatbot.di

import com.uhc.domain.chatbot.di.domainChatbotModule
import com.uhc.feature.chatbot.ChatbotViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureChatbotModule = module {
    includes(
        domainChatbotModule
    )

    viewModelOf(::ChatbotViewModel)
}
