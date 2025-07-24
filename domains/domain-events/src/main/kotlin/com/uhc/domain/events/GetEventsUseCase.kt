package com.uhc.domain.events

import com.uhc.api.events.EventApi
import com.uhc.repo.favourites.database.EventDao
import com.uhc.domain.events.model.Event

class GetEventsUseCase(
    private val serviceApi: EventApi,
    private val eventDao: EventDao
) {
    suspend fun getEvents(size: Int): List<Event> = serviceApi
        .getEvents(size)
        .embedded
        .events
        .map { eventDto ->
            val isFavourite = eventDao.findFavourite(eventDto.id) != null

            Event(
                id = eventDto.id,
                name = eventDto.name,
                imageUrl = eventDto.images.first().url,
                dates = eventDto.dates.start.localDate,
                venue = eventDto._embedded.venues.first().name,
                favourite = isFavourite
            )
        }
}
