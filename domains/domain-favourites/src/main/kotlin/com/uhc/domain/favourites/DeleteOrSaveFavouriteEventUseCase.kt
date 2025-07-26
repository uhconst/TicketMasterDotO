package com.uhc.domain.favourites

import com.uhc.repo.favourites.data.FavouriteEventEntity
import com.uhc.repo.favourites.database.EventDao

class DeleteOrSaveFavouriteEventUseCase(
    private val eventDao: EventDao
) {
    suspend operator fun invoke(id: String, isEventFavourite: Boolean) {
        if (isEventFavourite) {
            eventDao.deleteFavourite(FavouriteEventEntity(id))
        } else {
            eventDao.insertFavourite(FavouriteEventEntity(id))
        }
    }
}
