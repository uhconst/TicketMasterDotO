package com.uhc.domain.chatbot

import app.cash.turbine.test
import com.uhc.api.chatbot.ChatbotDataStore
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetApiKeyUseCaseTest {

    private val dataStore = mockk<ChatbotDataStore>()
    private val subject = GetApiKeyUseCase(dataStore)

    @Test
    fun `invoke returns flow of api key from data store`() = runTest {
        val expectedKey = "test-api-key"
        every { dataStore.getApiKey } returns flowOf(expectedKey)

        subject().test {
            assertThat(awaitItem()).isEqualTo(expectedKey)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns flow of null when api key is not in data store`() = runTest {
        every { dataStore.getApiKey } returns flowOf(null)

        subject().test {
            assertThat(awaitItem()).isNull()
            awaitComplete()
        }
    }
}
