package com.uhc.ticketmasterdoto.navigation

import com.uhc.lib.compose.utils.R
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

internal val TOP_LEVEL_ROUTES = mapOf<NavRoute, NavBarItem>(
    NavRoute.Home to NavBarItem(
        icon = R.drawable.home_24px,
        description = R.string.home_bottom_nav
    ),
    NavRoute.About to NavBarItem(
        icon = R.drawable.account_box_24px,
        description = R.string.about_bottom_nav
    ),
)