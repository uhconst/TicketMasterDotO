package com.uhc.domain.favourites.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class DomainFavouritesModuleTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `module should provide all dependencies`() {
        domainFavouritesModule.verify()
    }
}