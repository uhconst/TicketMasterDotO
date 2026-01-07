package com.uhc.feature.chatbot

import app.cash.turbine.test
import com.uhc.domain.chatbot.GetApiKeyUseCase
import com.uhc.domain.chatbot.SaveApiKeyUseCase
import com.uhc.domain.chatbot.SendMessageUseCase
import com.uhc.feature.chatbot.state.MessageState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatbotViewModelTest {

    private val sendMessageUseCase = mockk<SendMessageUseCase>()
    private val saveApiKeyUseCase = mockk<SaveApiKeyUseCase>(relaxed = true)
    private val getApiKeyUseCase = mockk<GetApiKeyUseCase>()

    private val apiKeyFlow = MutableStateFlow<String?>(null)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { getApiKeyUseCase() } returns apiKeyFlow
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = ChatbotViewModel(
        sendMessageUseCase,
        saveApiKeyUseCase,
        getApiKeyUseCase
    )

    @Test
    fun `init WITH null apiKey THEN shows dialog`() = runTest {
        apiKeyFlow.value = null
        val subject = createViewModel()

        subject.uiState.test {
            val state = awaitItem()
            assertThat(state.apiKey).isNull()
            assertThat(state.showDialog).isTrue()
        }
    }

    @Test
    fun `init WITH existing apiKey THEN does not show dialog`() = runTest {
        apiKeyFlow.value = "test-key"
        val subject = createViewModel()

        subject.uiState.test {
            val state = awaitItem()
            assertThat(state.apiKey).isEqualTo("test-key")
            assertThat(state.showDialog).isFalse()
        }
    }

    @Test
    fun `onSettingsClicked THEN shows dialog`() = runTest {
        apiKeyFlow.value = "test-key"
        val subject = createViewModel()

        subject.uiState.test {
            assertThat(awaitItem().showDialog).isFalse()
            subject.onSettingsClicked()
            assertThat(awaitItem().showDialog).isTrue()
        }
    }

    @Test
    fun `onDismissDialog THEN hides dialog`() = runTest {
        apiKeyFlow.value = null
        val subject = createViewModel()

        subject.uiState.test {
            assertThat(awaitItem().showDialog).isTrue()
            subject.onDismissDialog()
            assertThat(awaitItem().showDialog).isFalse()
        }
    }

    @Test
    fun `saveApiKey THEN calls use case and hides dialog`() = runTest {
        apiKeyFlow.value = null
        val subject = createViewModel()

        subject.uiState.test {
            assertThat(awaitItem().showDialog).isTrue()
            
            subject.saveApiKey("new-key")
            
            coVerify { saveApiKeyUseCase("new-key") }
            assertThat(awaitItem().showDialog).isFalse()
        }
    }

    @Test
    fun `sendMessage THEN adds user message and bot reply`() = runTest {
        apiKeyFlow.value = "key"
        coEvery { sendMessageUseCase("hello") } returns "hi there"
        val subject = createViewModel()

        subject.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.messages).isEmpty()

            subject.sendMessage("hello")

            val stateWithUserMsg = awaitItem()
            assertThat(stateWithUserMsg.messages).hasSize(1)
            assertThat(stateWithUserMsg.messages[0]).isInstanceOf(MessageState.User::class.java)
            assertThat(stateWithUserMsg.messages[0].text).isEqualTo("hello")

            val stateWithBotMsg = awaitItem()
            assertThat(stateWithBotMsg.messages).hasSize(2)
            assertThat(stateWithBotMsg.messages[1]).isInstanceOf(MessageState.Bot::class.java)
            assertThat(stateWithBotMsg.messages[1].text).isEqualTo("hi there")
        }
    }
}
