package com.uhc.feature.events

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.uhc.domain.events.model.Event
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class EventsLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun eventsList_displaysEventTexts() {
        val events = listOf(
            Event(
                id = "1",
                name = "Event One",
                imageUrl = "",
                dates = "2025-01-01",
                venue = "Venue One",
                favourite = false
            ),
            Event(
                id = "2",
                name = "Event Two",
                imageUrl = "",
                dates = "2025-01-02",
                venue = "Venue Two",
                favourite = true
            )
        )

        composeTestRule.setContent {
            TicketMasterTheme {
                EventsList(
                    events = events,
                    isLoading = false,
                    onRefresh = {},
                    onFavouriteClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Event One").assertExists()
        composeTestRule.onNodeWithText("Venue One").assertExists()
        composeTestRule.onNodeWithText("2025-01-01").assertExists()

        composeTestRule.onNodeWithText("Event Two").assertExists()
        composeTestRule.onNodeWithText("Venue Two").assertExists()
        composeTestRule.onNodeWithText("2025-01-02").assertExists()
    }

    @Test
    fun eventsList_showsFavouriteIconForEachItem_andClickCallsCallbackWithCorrectEvent() {
        val events = listOf(
            Event("1", "A", "", "D1", "V1", false),
            Event("2", "B", "", "D2", "V2", true),
            Event("3", "C", "", "D3", "V3", false),
        )
        var lastClicked: Event? = null

        composeTestRule.setContent {
            TicketMasterTheme {
                EventsList(
                    events = events,
                    isLoading = false,
                    onRefresh = {},
                    onFavouriteClick = { e -> lastClicked = e }
                )
            }
        }

        // There should be as many Favourite buttons as items
        composeTestRule.onAllNodesWithContentDescription("Favourite").assertCountEquals(events.size)

        // Click first favourite icon and verify callback receives first event
        val favourites = composeTestRule.onAllNodesWithContentDescription("Favourite")
        favourites[0].performClick()
        Assert.assertEquals(events.first(), lastClicked)
    }

    @Test
    fun eventItemCard_clickFavourite_invokesCallback() {
        val event = Event("10", "Solo Event", "", "2025-02-10", "Solo Venue", false)
        var invoked = false

        composeTestRule.setContent {
            TicketMasterTheme {
                EventItemCard(
                    event = event,
                    onFavouriteClick = { invoked = true }
                )
            }
        }

        composeTestRule.onAllNodesWithContentDescription("Favourite").get(0).performClick()

        Assert.assertTrue(invoked)
    }
}