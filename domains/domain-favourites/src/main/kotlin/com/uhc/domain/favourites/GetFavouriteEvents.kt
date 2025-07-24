package com.uhc.domain.favourites

import com.uhc.repo.favourites.database.EventDao
import com.uhc.repo.favourites.data.FavouriteEventEntity

class GetFavouriteEvents(
//    private val serviceApi: EventApi,
    private val eventDao: EventDao
) {
    suspend operator fun invoke(): List<FavouriteEventEntity> =
        eventDao.findFavourites()
/*            .map { entity ->
                val eventResponse = serviceApi.getEventById(entity.id)

                Event(
                    id = eventResponse.id,
                    name = eventResponse.name,
                    imageUrl = eventResponse.images.first().url,
                    dates = eventResponse.dates.start.localDate,
                    venue = eventResponse._embedded.venues.first().name,
                    favourite = true
                )
            }*/
}