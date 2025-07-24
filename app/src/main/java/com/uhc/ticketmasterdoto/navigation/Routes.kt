package com.uhc.ticketmasterdoto.navigation

sealed class Routes(val route: String) {
    data object Main: Routes("main_screen")
    data object Detail: Routes("detail_screen")
}