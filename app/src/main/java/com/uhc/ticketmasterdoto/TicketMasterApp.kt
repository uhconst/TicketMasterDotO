package com.uhc.ticketmasterdoto

import android.app.Application
import com.uhc.ticketmasterdoto.di.appModule
import com.uhc.ticketmasterdoto.di.dataModule
import com.uhc.ticketmasterdoto.di.domainModule
import com.uhc.ticketmasterdoto.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TicketMasterApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TicketMasterApp)
            modules(
                appModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}