package com.uhc.feature.events

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.uhc.domain.events.model.Event
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import org.junit.Rule
import org.junit.Test

class EventDetailsLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun eventDetails_displaysImageAndTexts() {
        val event = Event(
            id = "1",
            name = "Detail Event",
            imageUrl = "",
            dates = "2025-03-15",
            venue = "Detail Venue",
            favourite = false
        )

        composeTestRule.setContent {
            TicketMasterTheme {
                EventDetails(event = event)
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Event Image")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("event_name")
            .assertTextEquals("Detail Event")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("event_venue")
            .assertTextEquals("Detail Venue")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("event_dates")
            .assertTextEquals("2025-03-15")
            .assertIsDisplayed()
    }
}
