package com.uhc.domain.events

import app.cash.turbine.test
import com.uhc.api.events.EventApi
import com.uhc.api.events.data.EventsResponse
import com.uhc.repo.favourites.data.FavouriteEventEntity
import com.uhc.repo.favourites.database.EventDao
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetEventsUseCaseTest {

    private val eventApi: EventApi = mockk()
    private val eventDao: EventDao = mockk()
    private val subject = GetEventsUseCase(eventApi, eventDao)

    private fun makeEventResponse(
        id: String,
        name: String = "Event $id",
        imageUrl: String = "url$id",
        date: String = "2024-01-0$id",
        venue: String = "Venue $id"
    ) = EventsResponse.EventResponse(
        id = id,
        name = name,
        images = listOf(EventsResponse.ImageResponse(imageUrl)),
        dates = EventsResponse.DatesResponse(
            start = EventsResponse.StartResponse(date)
        ),
        _embedded = EventsResponse.EmbeddedVenuesResponse(
            venues = listOf(EventsResponse.VenueResponse(venue))
        )
    )

    @Test
    fun `emits events with correct favourite flags`() = runTest {
        val favourites = listOf(FavouriteEventEntity("1"))
        every { eventDao.findFavourites() } returns flowOf(favourites)
        coEvery { eventApi.getEvents(any()) } returns EventsResponse.Response(
            embedded = EventsResponse.EmbeddedResponse(
                events = listOf(
                    makeEventResponse("1"),
                    makeEventResponse("2")
                )
            )
        )

        subject().test {
            val events = awaitItem()
            assert(events.size == 2)
            assert(events[0].id == "1" && events[0].favourite)
            assert(events[1].id == "2" && !events[1].favourite)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits empty list when API returns no events`() = runTest {
        every { eventDao.findFavourites() } returns flowOf(emptyList())
        coEvery { eventApi.getEvents(any()) } returns EventsResponse.Response(
            embedded = EventsResponse.EmbeddedResponse(events = emptyList())
        )

        subject().test {
            val events = awaitItem()
            assert(events.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits events with all not favourite when no favourites`() = runTest {
        every { eventDao.findFavourites() } returns flowOf(emptyList())
        coEvery { eventApi.getEvents(any()) } returns EventsResponse.Response(
            embedded = EventsResponse.EmbeddedResponse(
                events = listOf(makeEventResponse("1"), makeEventResponse("2"))
            )
        )

        subject().test {
            val events = awaitItem()
            assert(events.all { !it.favourite })
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `maps all event fields correctly`() = runTest {
        every { eventDao.findFavourites() } returns flowOf(listOf(FavouriteEventEntity("42")))
        coEvery { eventApi.getEvents(any()) } returns EventsResponse.Response(
            embedded = EventsResponse.EmbeddedResponse(
                events = listOf(
                    makeEventResponse(
                        id = "42",
                        name = "My Event",
                        imageUrl = "img.png",
                        date = "2024-12-31",
                        venue = "My Venue"
                    )
                )
            )
        )

        subject().test {
            val event = awaitItem().first()
            assert(event.id == "42")
            assert(event.name == "My Event")
            assert(event.imageUrl == "img.png")
            assert(event.dates == "2024-12-31")
            assert(event.venue == "My Venue")
            assert(event.favourite)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uses custom size parameter`() = runTest {
        every { eventDao.findFavourites() } returns flowOf(emptyList())
        var calledWithSize: Int? = null
        coEvery { eventApi.getEvents(any()) } answers {
            calledWithSize = firstArg()
            EventsResponse.Response(
                embedded = EventsResponse.EmbeddedResponse(events = emptyList())
            )
        }

        subject(123).test {
            awaitItem()
            assert(calledWithSize == 123)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
