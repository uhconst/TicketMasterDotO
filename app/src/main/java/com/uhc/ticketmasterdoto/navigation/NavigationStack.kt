package com.uhc.ticketmasterdoto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uhc.feature.events.EventsScreen

@Composable
fun NavigationStack(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.Main.route
    ) {
        composable(route = Routes.Main.route) {
//            MainScreen(navController = navController)
            EventsScreen()
        }
        composable(
            route = Routes.Detail.route + "?text={text}",
            arguments = listOf(
                navArgument("text") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
//            DetailScreen(text = it.arguments?.getString("text"))
        }
    }
}
