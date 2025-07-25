package com.uhc.ticketmasterdoto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uhc.feature.about.AboutLayout
import com.uhc.feature.events.EventsLayout

@Composable
fun TicketMasterNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoute.Home.value
    ) {
        composable(route = NavRoute.Home.value) {
            EventsLayout()
        }
        composable(route = NavRoute.About.value) {
            AboutLayout()
        }
    }
}
