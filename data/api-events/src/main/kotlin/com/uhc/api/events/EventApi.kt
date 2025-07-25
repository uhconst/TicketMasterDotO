package com.uhc.api.events

import com.uhc.api.events.data.EventsResponse
import com.uhc.api.events.interceptor.RemoteRequestInterceptor
import com.uhc.lib.config.BuildConfigWrapper
import com.uhc.lib.network.utils.interceptors.ApiErrorInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * REST API for Event Api
 */
interface EventApi {

    /**
     * Attempts to get [EventsResponse.Response].
     */
    @GET("discovery/v2/events")
    suspend fun getEvents(
        @Query("size") size: Int
    ): EventsResponse.Response

    /**
     * Attempts to get [EventsResponse.Response].
     */
    @GET("discovery/v2/events/{id}")
    suspend fun getEventById(@Path("id") id: String): EventsResponse.EventResponse

    // todo move out of this class
    companion object {
        fun createEventService(
            buildConfigWrapper: BuildConfigWrapper,
            requestInterceptor: RemoteRequestInterceptor,
            apiErrorInterceptor: ApiErrorInterceptor
        ): EventApi {
            val client = OkHttpClient().newBuilder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(apiErrorInterceptor)
                .addInterceptor(getHttpLoggingInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(buildConfigWrapper.apiUrl)
                .build()
                .create(EventApi::class.java)
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }
    }
}