package com.uhc.domain.favourites

import com.uhc.repo.favourites.data.FavouriteEventEntity
import com.uhc.repo.favourites.database.EventDao
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SetFavouriteEventUseCaseTest {

    private val eventDao: EventDao = mockk(relaxed = true)
    private val subject = SetFavouriteEventUseCase(eventDao)

    @Test
    fun `invoke upsert favourite when isEventFavourite is true`() = runTest {
        val id = "event1"
        subject.invoke(id, true)
        coVerify { eventDao.upsertFavourite(FavouriteEventEntity(id, true)) }
    }

    @Test
    fun `invoke upsert favourite when isEventFavourite is false`() = runTest {
        val id = "event2"
        subject.invoke(id, false)
        coVerify { eventDao.upsertFavourite(FavouriteEventEntity(id, false)) }
    }
}
