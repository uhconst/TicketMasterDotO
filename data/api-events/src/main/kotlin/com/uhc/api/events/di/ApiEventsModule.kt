package com.uhc.api.events.di

import com.uhc.api.events.EventApi
import com.uhc.api.events.interceptor.RemoteRequestInterceptor
import com.uhc.lib.network.utils.interceptors.ApiErrorInterceptor
import org.koin.dsl.module

val apiEventsModule = module {
    factory { ApiErrorInterceptor() }

    factory { RemoteRequestInterceptor(get()) }

    single {
        EventApi.createEventService(
            get(),
            get(),
            get()
        )
    }
}
