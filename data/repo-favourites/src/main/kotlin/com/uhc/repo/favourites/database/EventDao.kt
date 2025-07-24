package com.uhc.repo.favourites.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uhc.repo.favourites.data.FavouriteEventEntity

/** Database access interface for [FavouriteEventEntity]. */
@Dao
interface EventDao {

    /** Insert the [FavouriteEventEntity] into the database. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favouriteEventEntity: FavouriteEventEntity)

    /** Delete favourite event. */
    @Delete
    suspend fun deleteFavourite(favouriteEventEntity: FavouriteEventEntity)

    /** Find favourite using the id provided. */
    @Query("SELECT * FROM favourite_event WHERE id = :id LIMIT 1")
    suspend fun findFavourite(id: String): FavouriteEventEntity?

    /** Find all favourites. */
    @Query("SELECT * FROM favourite_event")
    suspend fun findFavourites(): List<FavouriteEventEntity>
}