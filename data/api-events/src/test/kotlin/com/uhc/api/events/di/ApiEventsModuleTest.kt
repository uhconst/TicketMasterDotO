package com.uhc.api.events.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class ApiEventsModuleTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `module should provide all dependencies`() {
        apiEventsModule.verify()
    }
}
