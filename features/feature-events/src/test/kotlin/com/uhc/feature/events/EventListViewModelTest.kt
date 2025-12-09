package com.uhc.feature.events

import app.cash.turbine.test
import com.uhc.domain.events.GetEventsUseCase
import com.uhc.domain.events.model.Event
import com.uhc.domain.favourites.SetFavouriteEventUseCase
import com.uhc.feature.events.state.EventListState
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EventListViewModelTest {

    private val eventFavourite = Event("1", "Event 1", "url1", "2024-01-01", "Venue 1", true)
    private val eventNotFavourite = Event("2", "Event 2", "url2", "2024-01-02", "Venue 2", false)

    private val getEventsUseCase = mockk<GetEventsUseCase>(relaxed = true)

    private val setFavouriteEventUseCase =
        mockk<SetFavouriteEventUseCase>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private val subject = EventListViewModel(getEventsUseCase, setFavouriteEventUseCase)

    @Test
    fun `loadEvents emits Success when events are loaded`() = runTest {
        every { getEventsUseCase() } returns flowOf(listOf(eventFavourite, eventNotFavourite))

        subject.eventListState.test {
            assertThat(awaitItem()).isEqualTo(EventListState.Loading)
            subject.loadEvents()
            assertThat(awaitItem()).isEqualTo(
                EventListState.Success(
                    listOf(
                        eventFavourite,
                        eventNotFavourite
                    )
                )
            )
        }
    }

    @Test
    fun `loadEvents emits Error when events list is empty`() = runTest {
        every { getEventsUseCase() } returns flowOf(emptyList())

        subject.eventListState.test {
            assertThat(awaitItem()).isEqualTo(EventListState.Loading)
            subject.loadEvents()
            assertThat(awaitItem()).isEqualTo(EventListState.Error("No events found"))
        }
    }

    @Test
    fun `loadEvents emits Error on exception`() = runTest {
        every { getEventsUseCase() } returns flow { throw RuntimeException("Test error") }

        subject.eventListState.test {
            assertThat(awaitItem()).isEqualTo(EventListState.Loading)
            subject.loadEvents()
            assertThat(awaitItem()).isEqualTo(EventListState.Error("Test error"))
        }
    }

    @Test
    fun `onClickFavouriteEvent WITH event favourite true THEN calls setFavouriteEvent use case`() =
        runTest(testDispatcher) {
            subject.onClickFavouriteEvent(eventFavourite)
            coVerify { setFavouriteEventUseCase("1", false) }
        }

    @Test
    fun `onClickFavouriteEvent WITH event favourite false THEN calls setFavouriteEvent use case`() =
        runTest(testDispatcher) {
            subject.onClickFavouriteEvent(eventNotFavourite)
            coVerify { setFavouriteEventUseCase("2", true) }
        }
}
