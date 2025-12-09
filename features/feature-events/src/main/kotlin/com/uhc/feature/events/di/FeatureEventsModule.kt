package com.uhc.feature.events.di

import com.uhc.domain.events.di.domainEventsModule
import com.uhc.domain.favourites.di.domainFavouritesModule
import com.uhc.feature.events.EventDetailsViewModel
import com.uhc.feature.events.EventListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureEventsModule = module {
    includes(
        domainEventsModule,
        domainFavouritesModule
    )

    viewModelOf(::EventListViewModel)
    viewModel { (eventId: String) ->
        EventDetailsViewModel(get(), eventId)
    }
}
