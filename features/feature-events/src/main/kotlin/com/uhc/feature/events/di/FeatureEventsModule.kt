package com.uhc.feature.events.di

import com.uhc.domain.events.di.domainEventsModule
import com.uhc.domain.favourites.di.domainFavouritesModule
import com.uhc.feature.events.EventListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureEventsModule = module {
    includes(
        domainEventsModule,
        domainFavouritesModule
    )

    viewModel { EventListViewModel(get(), get()) }
}
