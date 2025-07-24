package com.uhc.ticketmasterdoto

import android.app.Application
import com.uhc.feature.events.di.featureEventsModule
import com.uhc.ticketmasterdoto.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TicketMasterApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TicketMasterApp)
            modules(
                appModule,
                featureEventsModule
            )
        }
    }
}