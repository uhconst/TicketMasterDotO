package com.uhc.ticketmasterdoto.di

import com.uhc.presentation.ui.EventListViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val presentationModule = module {
    viewModel { EventListViewModel(get(), get()) }
//    viewModel { FavouriteEventListViewModel(get()) }
//    factory { EventRecyclerAdapter() }
}