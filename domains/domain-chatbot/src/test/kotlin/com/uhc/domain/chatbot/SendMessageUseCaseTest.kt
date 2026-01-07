package com.uhc.domain.chatbot

import com.uhc.api.chatbot.ChatbotRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SendMessageUseCaseTest {

    private val repository = mockk<ChatbotRepository>()
    private val subject = SendMessageUseCase(repository)

    @Test
    fun `invoke calls repository sendMessage and cleans markdown`() = runTest {
        val input = "Hello"
        val rawReply = "This is **bold** and *italic* reply."
        val expectedReply = "This is bold and italic reply."
        
        coEvery { repository.sendMessage(input) } returns rawReply

        val result = subject(input)

        assertThat(result).isEqualTo(expectedReply)
    }

    @Test
    fun `invoke returns repository default reply if none provided`() = runTest {
        val input = "Hello"
        val defaultReply = "Sorry, I couldn't come up with a reply."
        
        coEvery { repository.sendMessage(input) } returns defaultReply

        val result = subject(input)

        assertThat(result).isEqualTo(defaultReply)
    }
}
