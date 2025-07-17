package com.uhc.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uhc.data.local.event.EventDao
import com.uhc.data.local.event.FavouriteEventEntity

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