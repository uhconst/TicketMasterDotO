package com.uhc.ticketmasterdoto.di

import com.uhc.lib.config.BuildConfigWrapper
import com.uhc.ticketmasterdoto.util.BuildConfigWrapperImpl
import org.koin.dsl.module

val appModule = module {
    single<BuildConfigWrapper> { BuildConfigWrapperImpl() }
}
