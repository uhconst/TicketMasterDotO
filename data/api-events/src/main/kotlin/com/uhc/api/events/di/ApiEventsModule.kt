package com.uhc.api.events.di

import com.uhc.api.events.EventApi
import com.uhc.api.events.interceptor.RemoteErrorInterceptor
import com.uhc.api.events.interceptor.RemoteRequestInterceptor
import org.koin.dsl.module

val apiEventsModule = module {
    factory { RemoteErrorInterceptor() }

    factory { RemoteRequestInterceptor(get()) }

    single {
        EventApi.createEventService(
            get(),
            get()
        )
    }
}