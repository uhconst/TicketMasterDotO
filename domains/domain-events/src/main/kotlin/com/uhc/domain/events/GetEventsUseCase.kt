package com.uhc.domain.events

import com.uhc.api.events.EventApi
import com.uhc.domain.events.model.Event
import com.uhc.repo.favourites.database.EventDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

private var defaultSize: Int = 50

class GetEventsUseCase(
    private val serviceApi: EventApi,
    private val eventDao: EventDao
) {
    operator fun invoke(size: Int = defaultSize): Flow<List<Event>> = flow {
        val baseEvents = serviceApi
            .getEvents(size)
            .embedded
            .events
            .map { event ->
                Event(
                    id = event.id,
                    name = event.name,
                    imageUrl = event.images.first().url,
                    dates = event.dates.start.localDate,
                    venue = event._embedded.venues.first().name,
                    favourite = false
                )
            }

        // Emit updated lists whenever favourites change, without re-calling the API
        emitAll(
            eventDao
                .findFavourites()
                .map { favourites ->
                    val favouriteIds = favourites
                        .map { it.id }
                        .toSet()
                    baseEvents
                        .map { it.copy(favourite = favouriteIds.contains(it.id)) }
                        .sortedByDescending { it.favourite }
                }
        )
    }
}
