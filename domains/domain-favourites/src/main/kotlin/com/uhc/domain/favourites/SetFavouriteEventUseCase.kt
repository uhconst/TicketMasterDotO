package com.uhc.domain.favourites

import com.uhc.repo.favourites.data.FavouriteEventEntity
import com.uhc.repo.favourites.database.EventDao

class SetFavouriteEventUseCase(
    private val eventDao: EventDao
) {
    suspend operator fun invoke(id: String, isEventFavourite: Boolean) {
        eventDao.upsertFavourite(
            FavouriteEventEntity(
                id = id,
                isFavourite = isEventFavourite
            )
        )
    }
}
