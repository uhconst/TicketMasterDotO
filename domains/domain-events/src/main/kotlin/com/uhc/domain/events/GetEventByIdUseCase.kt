package com.uhc.domain.events

import com.uhc.api.events.EventApi
import com.uhc.domain.events.model.Event

class GetEventByIdUseCase(
    private val serviceApi: EventApi
) {
    suspend operator fun invoke(eventId: String): Event {
        val response = serviceApi.getEventById(eventId)

        return Event(
            id = response.id,
            name = response.name,
            imageUrl = response.images.firstOrNull()?.url ?: "",
            dates = response.dates.start.localDate,
            venue = response._embedded.venues.firstOrNull()?.name ?: "",
            favourite = false
        )
    }
}
