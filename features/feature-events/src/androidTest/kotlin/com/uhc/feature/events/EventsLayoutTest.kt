package com.uhc.feature.events

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.uhc.domain.events.model.Event
import com.uhc.feature.events.state.EventState
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class EventsLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun eventsList_displaysEventTexts() {
        val events = listOf(
            Event("1", "Event One", "", "2025-01-01", "Venue One", false),
            Event("2", "Event Two", "", "2025-01-02", "Venue Two", true)
        )

        composeTestRule.setContent {
            TicketMasterTheme {
                EventsList(
                    eventState = EventState.Success(events),
                    onRefresh = {},
                    onFavouriteClick = {}
                )
            }
        }
        val eventNames = composeTestRule.onAllNodesWithTag("event_name")
        eventNames.assertCountEquals(2)
        eventNames[0].assertTextEquals("Event One").assertIsDisplayed()
        eventNames[1].assertTextEquals("Event Two").assertIsDisplayed()

        val eventVenues = composeTestRule.onAllNodesWithTag("event_venue")
        eventVenues.assertCountEquals(2)
        eventVenues[0].assertTextEquals("Venue One").assertIsDisplayed()
        eventVenues[1].assertTextEquals("Venue Two").assertIsDisplayed()

        val eventDates = composeTestRule.onAllNodesWithTag("event_dates")
        eventDates.assertCountEquals(2)
        eventDates[0].assertTextEquals("2025-01-01").assertIsDisplayed()
        eventDates[1].assertTextEquals("2025-01-02").assertIsDisplayed()
    }

    @Test
    fun eventsList_showsFavouriteIconForEachItem_andClickCallsCallbackWithCorrectEvent() {
        val events = listOf(
            Event("1", "A", "", "D1", "V1", false),
            Event("2", "B", "", "D2", "V2", true),
            Event("3", "C", "", "D3", "V3", false),
        )
        var favouriteClicked: Event? = null

        composeTestRule.setContent {
            TicketMasterTheme {
                EventsList(
                    eventState = EventState.Success(events),
                    onRefresh = {},
                    onFavouriteClick = { favouriteClicked = it }
                )
            }
        }

        // There should be as many Favourite buttons as items
        composeTestRule.onAllNodesWithTag("event_favourite_icon").assertCountEquals(events.size)

        // Click first favourite icon and verify callback receives first event
        composeTestRule.onAllNodesWithTag("event_favourite_icon")[0].performClick()
        assertThat(favouriteClicked).isEqualTo(events.first())
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

        composeTestRule.onNodeWithTag("event_favourite_icon").performClick()

        assertThat(invoked).isTrue()
    }

    @Test
    fun eventsError_displaysErrorMessageAndRetryButton() {
        val errorMessage = "Something went wrong"
        composeTestRule.setContent {
            TicketMasterTheme {
                EventsError(error = EventState.Error(errorMessage))
            }
        }
        composeTestRule
            .onNodeWithTag("error_message")
            .assertTextEquals(errorMessage)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("retry_button")
            .assertIsDisplayed()
    }

    @Test
    fun eventsError_retryButtonClick() {
        val errorMessage = "Something went wrong"
        var invoked = false
        composeTestRule.setContent {
            TicketMasterTheme {
                EventsError(
                    error = EventState.Error(errorMessage),
                    onRetry = { invoked = true }
                )
            }
        }
        composeTestRule
            .onNodeWithTag("retry_button")
            .performClick()

        assertThat(invoked).isTrue()
    }

    @Test
    fun eventsLoading_displaysLoadingIndicatorAndText() {
        composeTestRule.setContent {
            TicketMasterTheme {
                EventsLoading()
            }
        }
        composeTestRule
            .onNodeWithTag("loading_text")
            .assertTextEquals("Loading...")
            .assertIsDisplayed()
    }
}