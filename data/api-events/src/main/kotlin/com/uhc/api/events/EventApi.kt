package com.uhc.api.events

import com.uhc.api.events.data.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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
}