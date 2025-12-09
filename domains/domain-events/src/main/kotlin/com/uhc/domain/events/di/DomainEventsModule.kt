package com.uhc.domain.events.di

import com.uhc.api.events.di.apiEventsModule
import com.uhc.domain.events.GetEventByIdUseCase
import com.uhc.domain.events.GetEventsUseCase
import com.uhc.repo.favourites.di.repoFavouritesModule
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainEventsModule = module {
    includes(
        apiEventsModule,
        repoFavouritesModule
    )
    factoryOf(::GetEventsUseCase)
    factoryOf(::GetEventByIdUseCase)
}
