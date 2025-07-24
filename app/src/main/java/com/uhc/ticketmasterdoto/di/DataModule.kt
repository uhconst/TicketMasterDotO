package com.uhc.ticketmasterdoto.di

import com.uhc.data.local.AppDatabase
import com.uhc.data.remote.EventService
import com.uhc.data.remote.interceptor.RemoteErrorInterceptor
import com.uhc.data.remote.interceptor.RemoteRequestInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import com.uhc.ticketmasterdoto.BuildConfig

val dataModule = module {

    factory { RemoteErrorInterceptor() }

    factory { RemoteRequestInterceptor(BuildConfig.API_KEY) }

    single {
        AppDatabase.createDatabase(
            androidApplication(),
            "AppDatabase"
        )
    }

    single { get<AppDatabase>().eventDao() }

    single {
        EventService.createEventService(
            BuildConfig.API_URL,
            get(),
            get()
        )
    }
}