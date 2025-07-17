package com.uhc.data.repository

import com.uhc.data.local.event.EventDao
import com.uhc.data.local.event.FavouriteEventEntity
import com.uhc.data.remote.EventService
import com.uhc.domain.model.Event
import com.uhc.domain.repository.EventRepository

/**
 * Event Repository Implementation
 */
class EventRepositoryImpl(
    private val service: EventService,
    private val dao: EventDao
) : EventRepository {

    override suspend fun getEvents(size: Int): List<Event> {
        val response = service.getEvents(size)
        return response.embedded.events.map { eventDto ->
            val isFavourite = dao.findFavourite(eventDto.id) != null
            eventDto.toEvent(isFavourite)
        }
    }

    override suspend fun saveFavouriteEvent(event: Event) {
        dao.insertFavourite(FavouriteEventEntity(event.id))
    }

    override suspend fun deleteFavouriteEvent(id: String) {
        dao.deleteFavourite(FavouriteEventEntity(id))
    }

    override suspend fun getFavouritesEvent(): List<Event> {
        val favouriteEntities = dao.findFavourites()
        return favouriteEntities.map { entity ->
            val eventResponse = service.getEventById(entity.id)
            eventResponse.toEvent(true)
        }
    }
}