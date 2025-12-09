package com.uhc.api.events.di

import com.uhc.api.events.EventApiFactory
import com.uhc.api.events.interceptor.RemoteRequestInterceptor
import com.uhc.lib.network.utils.interceptors.ApiErrorInterceptor
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val apiEventsModule = module {
    factoryOf(::ApiErrorInterceptor)
    factoryOf(::RemoteRequestInterceptor)

    singleOf(EventApiFactory::createEventService)
}
