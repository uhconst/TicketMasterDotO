package com.uhc.domain.events.di

import com.uhc.api.events.di.apiEventsModule
import com.uhc.repo.favourites.di.repoFavouritesModule
import com.uhc.domain.events.GetEventsUseCase
import org.koin.dsl.module

val domainEventsModule = module {
    includes(
        apiEventsModule,
        repoFavouritesModule
    )
    factory { GetEventsUseCase(get(), get()) }
}
