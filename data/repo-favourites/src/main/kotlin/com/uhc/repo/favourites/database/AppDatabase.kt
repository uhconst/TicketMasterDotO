package com.uhc.repo.favourites.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uhc.repo.favourites.data.FavouriteEventEntity

@Database(entities = [FavouriteEventEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(application: Application, databaseName: String): AppDatabase =
            Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                databaseName
            ).allowMainThreadQueries().build()

    }

    abstract fun eventDao(): EventDao
}