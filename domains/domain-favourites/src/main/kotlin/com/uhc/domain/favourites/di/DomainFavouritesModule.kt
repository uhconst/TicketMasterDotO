package com.uhc.domain.favourites.di

import com.uhc.domain.favourites.DeleteFavouriteEventUseCase
import com.uhc.domain.favourites.GetFavouriteEvents
import com.uhc.domain.favourites.SaveFavouriteEventUseCase
import org.koin.dsl.module

val domainFavouritesModule = module {
    factory { DeleteFavouriteEventUseCase(get()) }
    factory { GetFavouriteEvents(get()) }
    factory { SaveFavouriteEventUseCase(get()) }
}
