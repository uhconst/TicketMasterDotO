package com.uhc.domain.favourites

import com.uhc.repo.favourites.data.FavouriteEventEntity
import com.uhc.repo.favourites.database.EventDao
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteOrSaveFavouriteEventUseCaseTest {

    private val eventDao: EventDao = mockk(relaxed = true)
    private val subject = DeleteOrSaveFavouriteEventUseCase(eventDao)

    @Test
    fun `invoke deletes favourite when isEventFavourite is true`() = runTest {
        val id = "event1"
        subject.invoke(id, true)
        coVerify { eventDao.deleteFavourite(FavouriteEventEntity(id)) }
    }

    @Test
    fun `invoke inserts favourite when isEventFavourite is false`() = runTest {
        val id = "event2"
        subject.invoke(id, false)
        coVerify { eventDao.insertFavourite(FavouriteEventEntity(id)) }
    }
}
