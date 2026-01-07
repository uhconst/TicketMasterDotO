package com.uhc.domain.chatbot

import com.uhc.api.chatbot.ChatbotDataStore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveApiKeyUseCaseTest {

    private val dataStore = mockk<ChatbotDataStore>()
    private val subject = SaveApiKeyUseCase(dataStore)

    @Test
    fun `invoke calls saveApiKey in data store`() = runTest {
        val apiKey = "new-api-key"
        coEvery { dataStore.saveApiKey(apiKey) } returns Unit

        subject(apiKey)

        coVerify { dataStore.saveApiKey(apiKey) }
    }
}
