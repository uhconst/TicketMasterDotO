package com.uhc.domain.chatbot.utils

/**
 * Remove Markdown-style bold/italic symbols like **text** or *text*
 */
internal fun String.cleanMarkdown(): String =
    this.replace(Regex("\\*{1,2}"), "")
