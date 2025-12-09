package com.uhc.ticketmasterdoto.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute {
    @Serializable
    data object Home: NavRoute()
    @Serializable
    data object About: NavRoute()
    @Serializable
    data class EventDetails(val eventId: String): NavRoute()
}