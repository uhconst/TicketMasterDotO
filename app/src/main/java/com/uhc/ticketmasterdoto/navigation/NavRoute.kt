package com.uhc.ticketmasterdoto.navigation

sealed class NavRoute(val value: String) {
    data object Home: NavRoute("home")
    data object About: NavRoute("about")
}