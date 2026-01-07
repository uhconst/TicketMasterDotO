package com.uhc.feature.chatbot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.uhc.feature.chatbot.state.ChatbotUiState
import com.uhc.feature.chatbot.state.MessageState
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class ChatbotLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun chatbotScreen_whenNoApiKey_showsEnterApiKeyButton() {
        composeTestRule.setContent {
            TicketMasterTheme {
                ChatbotScreen(
                    uiState = ChatbotUiState(apiKey = null),
                    onSettingsClicked = {},
                    onDismissDialog = {},
                    onSaveApiKey = {},
                    onSendMessage = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("enter_api_key_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("message_input_area").assertDoesNotExist()
    }

    @Test
    fun chatbotScreen_whenHasApiKey_showsMessageInputArea() {
        composeTestRule.setContent {
            TicketMasterTheme {
                ChatbotScreen(
                    uiState = ChatbotUiState(apiKey = "some-key"),
                    onSettingsClicked = {},
                    onDismissDialog = {},
                    onSaveApiKey = {},
                    onSendMessage = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("message_input_area").assertIsDisplayed()
        composeTestRule.onNodeWithTag("enter_api_key_button").assertDoesNotExist()
    }

    @Test
    fun chatbotScreen_whenShowDialogIsTrue_showsApiKeyDialog() {
        composeTestRule.setContent {
            TicketMasterTheme {
                ChatbotScreen(
                    uiState = ChatbotUiState(showDialog = true),
                    onSettingsClicked = {},
                    onDismissDialog = {},
                    onSaveApiKey = {},
                    onSendMessage = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("api_key_dialog").assertIsDisplayed()
    }

    @Test
    fun chatbotApiKeyDialog_submitCallsOnConfirmWithInputText() {
        var submittedKey = ""
        composeTestRule.setContent {
            TicketMasterTheme {
                ChatbotApiKeyDialog(
                    currentApiKey = "",
                    onDismissRequest = {},
                    onConfirm = { submittedKey = it }
                )
            }
        }

        composeTestRule.onNodeWithTag("api_key_input").performTextInput("test-api-key")
        composeTestRule.onNodeWithTag("api_key_submit").performClick()

        assertThat(submittedKey).isEqualTo("test-api-key")
    }

    @Test
    fun chatbotApiKeyDialog_cancelCallsOnDismissRequest() {
        var dismissed = false
        composeTestRule.setContent {
            TicketMasterTheme {
                ChatbotApiKeyDialog(
                    currentApiKey = "",
                    onDismissRequest = { dismissed = true },
                    onConfirm = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("api_key_cancel").performClick()

        assertThat(dismissed).isTrue()
    }

    @Test
    fun messageInput_sendButtonClickCallsOnSendMessage() {
        var sentMessage = ""
        composeTestRule.setContent {
            TicketMasterTheme {
                MessageInput(
                    hasApiKey = true,
                    onSendMessage = { sentMessage = it },
                    onSettingsClicked = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("message_input_field").performTextInput("Hello Ury")
        composeTestRule.onNodeWithTag("send_button").performClick()

        assertThat(sentMessage).isEqualTo("Hello Ury")
    }

    @Test
    fun messageList_displaysMessages() {
        val messages = listOf(
            MessageState.User("Hello"),
            MessageState.Bot("Hi there")
        )

        composeTestRule.setContent {
            TicketMasterTheme {
                MessageList(messages = messages)
            }
        }

        composeTestRule.onNodeWithTag("user_message").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bot_message").assertIsDisplayed()
    }

    @Test
    fun chatbotHeader_settingsClickCallsOnSettingsClicked() {
        var settingsClicked = false
        composeTestRule.setContent {
            TicketMasterTheme {
                ChatbotHeader(onSettingsClicked = { settingsClicked = true })
            }
        }

        composeTestRule.onNodeWithTag("settings_button").performClick()

        assertThat(settingsClicked).isTrue()
    }
}
