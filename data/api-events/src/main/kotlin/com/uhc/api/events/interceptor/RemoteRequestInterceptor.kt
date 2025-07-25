package com.uhc.api.events.interceptor

import com.uhc.lib.config.BuildConfigWrapper
import okhttp3.Interceptor
import okhttp3.Response

class RemoteRequestInterceptor(
    private val buildConfigWrapper: BuildConfigWrapper
) : Interceptor {

    companion object {
        private const val API_KEY = "apikey"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain
            .request()
            .url
            .newBuilder()
            .addQueryParameter(API_KEY, buildConfigWrapper.apiKey)
            .build()

        val request = chain
            .request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
