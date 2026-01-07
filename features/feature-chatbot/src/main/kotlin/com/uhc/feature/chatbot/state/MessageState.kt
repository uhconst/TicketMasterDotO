package com.uhc.feature.chatbot.state

sealed class MessageState(val text: String) {
    class User(text: String) : MessageState(text)
    class Bot(text: String) : MessageState(text)
}
