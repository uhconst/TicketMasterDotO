package com.uhc.repo.favourites.di

import com.uhc.repo.favourites.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repoFavouritesModule = module {
    single {
        AppDatabase.createDatabase(
            androidApplication(),
            "AppDatabase"
        )
    }

    single { get<AppDatabase>().eventDao() }
}
