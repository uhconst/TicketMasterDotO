package com.uhc.domain.favourites

import com.uhc.repo.favourites.database.EventDao
import com.uhc.repo.favourites.data.FavouriteEventEntity

class SaveFavouriteEventUseCase(
    private val eventDao: EventDao
) {
    suspend operator fun invoke(id: String) {
        eventDao.insertFavourite(FavouriteEventEntity(id))
    }
}