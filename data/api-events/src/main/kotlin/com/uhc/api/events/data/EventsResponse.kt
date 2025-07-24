package com.uhc.api.events.data

import com.google.gson.annotations.SerializedName

sealed class EventsResponse {
    /**
     * Represent a single Response.
     */
    data class Response(
        @SerializedName("_embedded") val embedded: EmbeddedResponse
    )

    /**
     * Represent a single EmbeddedResponse.
     */
    data class EmbeddedResponse(
        val events: List<EventResponse>
    )

    /**
     * Represent a single EventResponse.
     */
    data class EventResponse(
        val id: String,
        val name: String,
        val dates: DatesResponse,
        val images: List<ImageResponse>,
        val _embedded: EmbeddedVenuesResponse
    )

    /**
     * Represent a single DatesResponse.
     */
    data class DatesResponse(
        val start: StartResponse
    )

    /**
     * Represent a single StartResponse.
     */
    data class StartResponse(
        val localDate: String
    )

    /**
     * Represent a single ImageResponse.
     */
    data class ImageResponse(
        val url: String
    )

    /**
     * Represent a single EmbeddedVenuesResponse.
     */
    data class EmbeddedVenuesResponse(
        val venues: List<VenueResponse>
    )

    /**
     * Represent a single VenueResponse.
     */
    data class VenueResponse(
        val name: String
    )
}