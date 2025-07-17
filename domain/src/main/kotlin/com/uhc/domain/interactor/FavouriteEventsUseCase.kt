package com.uhc.domain.interactor

import com.uhc.domain.model.Event
import com.uhc.domain.repository.EventRepository

class FavouriteEventsUseCase(
    private val eventRepository: EventRepository
) {

    suspend fun updateFavouriteEvent(
        event: Event
    ) = if (event.favourite) {
        deleteFavouriteEvent(event)
    } else {
        saveFavouriteEvent(event)
    }

    private suspend fun saveFavouriteEvent(
        event: Event
    ) = eventRepository.saveFavouriteEvent(event)

    private suspend fun deleteFavouriteEvent(
        event: Event
    ) = eventRepository.deleteFavouriteEvent(event.id)

    suspend fun getFavouriteEvents(): List<Event> = eventRepository.getFavouritesEvent()
}