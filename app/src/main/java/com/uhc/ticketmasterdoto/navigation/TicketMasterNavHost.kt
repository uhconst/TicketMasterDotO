package com.uhc.ticketmasterdoto.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.uhc.feature.about.AboutLayout
import com.uhc.feature.events.EventDetailsLayout
import com.uhc.feature.events.EventsLayout
import com.uhc.lib.compose.utils.theme.LocalAnimatedVisibilityScope
import com.uhc.lib.compose.utils.theme.LocalSharedTransitionScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TicketMasterNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    SharedTransitionLayout {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = NavRoute.Home
        ) {
            composable<NavRoute.Home> {
                CompositionLocalProvider(
                    LocalSharedTransitionScope provides this@SharedTransitionLayout,
                    LocalAnimatedVisibilityScope provides this@composable
                ) {
                    EventsLayout(
                        onEventClick = { eventId ->
                            navController.navigate(NavRoute.EventDetails(eventId))
                        }
                    )
                }
            }
            composable<NavRoute.EventDetails> { backStackEntry ->
                val args = backStackEntry.toRoute<NavRoute.EventDetails>()
                CompositionLocalProvider(
                    LocalSharedTransitionScope provides this@SharedTransitionLayout,
                    LocalAnimatedVisibilityScope provides this@composable
                ) {
                    EventDetailsLayout(
                        eventId = args.eventId
                    )
                }
            }
            composable<NavRoute.About> {
                AboutLayout()
            }
        }
    }
}
