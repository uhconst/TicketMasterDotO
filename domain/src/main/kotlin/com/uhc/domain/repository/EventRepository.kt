package com.uhc.domain.repository

import com.uhc.domain.model.Event

interface EventRepository {
    suspend fun getEvents(size: Int): List<Event>

    suspend fun saveFavouriteEvent(event: Event)

    suspend fun deleteFavouriteEvent(id: String)

    suspend fun getFavouritesEvent(): List<Event>
}