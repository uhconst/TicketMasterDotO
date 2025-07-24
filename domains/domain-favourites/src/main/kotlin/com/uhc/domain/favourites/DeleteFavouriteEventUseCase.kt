package com.uhc.domain.favourites

import com.uhc.repo.favourites.data.FavouriteEventEntity
import com.uhc.repo.favourites.database.EventDao

class DeleteFavouriteEventUseCase(
    private val eventDao: EventDao
) {
    suspend operator fun invoke(id: String) {
        eventDao.deleteFavourite(FavouriteEventEntity(id))
    }
}
