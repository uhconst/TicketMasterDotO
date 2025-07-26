package com.uhc.feature.events

import com.uhc.domain.events.GetEventsUseCase
import com.uhc.domain.events.model.Event
import com.uhc.domain.favourites.DeleteOrSaveFavouriteEventUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EventListViewModelTest {

    private val eventFavourite = Event("1", "Event 1", "url1", "2024-01-01", "Venue 1", true)
    private val eventNotFavourite = Event("2", "Event 2", "url2", "2024-01-02", "Venue 2", false)

    private val getEventsUseCase: GetEventsUseCase = mockk()

    private val deleteOrSaveFavouriteEventUseCase: DeleteOrSaveFavouriteEventUseCase =
        mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private val subject = EventListViewModel(getEventsUseCase, deleteOrSaveFavouriteEventUseCase)

    @Test
    fun `onClickFavouriteEvent WITH event favourite true THEN calls deleteOrSave use case`() =
        runTest(testDispatcher) {
            every { getEventsUseCase.invoke(any()) } returns flowOf(listOf(eventFavourite))
            subject.onClickFavouriteEvent(eventFavourite)
            coVerify { deleteOrSaveFavouriteEventUseCase("1", true) }
        }

    @Test
    fun `onClickFavouriteEvent WITH event favourite false THEN calls deleteOrSave use case`() =
        runTest(testDispatcher) {
            every { getEventsUseCase.invoke(any()) } returns flowOf(listOf(eventNotFavourite))
            subject.onClickFavouriteEvent(eventFavourite)
            coVerify { deleteOrSaveFavouriteEventUseCase("2", false) }
        }
}
