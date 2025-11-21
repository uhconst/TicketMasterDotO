package com.uhc.repo.favourites.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single Event.
 *
 * @property id Unique identifier of the favourite event in the DB.
 * @property isFavourite Whether the event is a favourite or not.
 */
@Entity(tableName = "favourite_event")
data class FavouriteEventEntity(
    @PrimaryKey val id: String,
    val isFavourite: Boolean
)