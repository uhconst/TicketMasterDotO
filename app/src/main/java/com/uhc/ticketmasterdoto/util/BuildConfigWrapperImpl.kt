package com.uhc.ticketmasterdoto.util

import com.uhc.lib.config.BuildConfigWrapper
import com.uhc.ticketmasterdoto.BuildConfig

class BuildConfigWrapperImpl : BuildConfigWrapper {
    override val apiKey: String
        get() = BuildConfig.API_KEY

    override val apiUrl: String
        get() = BuildConfig.API_URL
}
