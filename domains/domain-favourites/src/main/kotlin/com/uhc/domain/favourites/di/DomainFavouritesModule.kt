package com.uhc.domain.favourites.di

import com.uhc.domain.favourites.DeleteOrSaveFavouriteEventUseCase
import org.koin.dsl.module

val domainFavouritesModule = module {
    factory { DeleteOrSaveFavouriteEventUseCase(get()) }
}
