package com.uhc.feature.about

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class AboutLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun aboutLayout_displaysExpectedTexts() {
        composeTestRule.setContent {
            TicketMasterTheme {
                AboutLayout()
            }
        }

        with(composeTestRule) {
            onNodeWithTag("about_name")
                .assertTextEquals("Uryel Constancio")
                .assertIsDisplayed()
            onNodeWithTag("about_role")
                .assertTextEquals("Senior Android Developer")
                .assertIsDisplayed()

            onNodeWithTag("about_bio").assertIsDisplayed()

            onNodeWithTag("about_this_app_title")
                .assertTextEquals("About this app")
                .assertIsDisplayed()
            onNodeWithTag("about_app_description")
                .assertTextEquals("This app follows clean architecture with data, domain, and feature (UI) modules. It uses Room database, Retrofit, and is built fully in Kotlin and Jetpack Compose.")
                .assertIsDisplayed()
        }
    }

    @Test
    fun aboutLayout_clickingButtons_opensCorrectUrls() {
        var lastOpenedUrl: String? = null
        val fakeUriHandler = object : UriHandler {
            override fun openUri(uri: String) {
                lastOpenedUrl = uri
            }
        }

        composeTestRule.setContent {
            CompositionLocalProvider(LocalUriHandler provides fakeUriHandler) {
                TicketMasterTheme {
                    AboutLayout()
                }
            }
        }

        // Click GitHub and verify URL
        composeTestRule.onNodeWithTag("about_github_button").performClick()
        assertThat(lastOpenedUrl).isEqualTo("https://github.com/uhconst")

        // Click LinkedIn and verify URL
        composeTestRule.onNodeWithTag("about_linkedin_button").performClick()
        assertThat(lastOpenedUrl).isEqualTo("https://www.linkedin.com/in/uryel-constancio-49247384/")
    }
}
