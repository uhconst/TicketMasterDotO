package com.uhc.domain.favourites.di

import com.uhc.domain.favourites.DeleteOrSaveFavouriteEventUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainFavouritesModule = module {
    factoryOf(::DeleteOrSaveFavouriteEventUseCase)
}
