package com.uhc.domain.events

import com.uhc.api.events.EventApi
import com.uhc.domain.events.model.Event
import com.uhc.repo.favourites.database.EventDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

private var defaultSize: Int = 50

class GetEventsUseCase(
    private val serviceApi: EventApi,
    private val eventDao: EventDao
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(size: Int = defaultSize): Flow<List<Event>> =
        eventDao
            .findFavourites()
            .flatMapLatest { favourites ->
                flow {
                    val favouriteIds = favourites.map { it.id }
                    val events = serviceApi
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
                                favourite = favouriteIds.contains(event.id)
                            )
                        }
                        .sortedByDescending { it.favourite }
                    emit(events)
                }
            }
}
