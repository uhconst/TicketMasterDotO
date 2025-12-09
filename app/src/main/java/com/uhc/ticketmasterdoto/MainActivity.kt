package com.uhc.ticketmasterdoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.uhc.lib.compose.utils.R
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import com.uhc.ticketmasterdoto.navigation.NavRoute
import com.uhc.ticketmasterdoto.navigation.TicketMasterNavHost
import kotlin.reflect.KClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry by navController.currentBackStackEntryAsState()

            TicketMasterTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentBackStackEntry?.destination.isRouteInHierarchy(
                                    NavRoute.Home::class
                                ),
                                onClick = { navController.navigate(NavRoute.Home) },
                                label = { Text(stringResource(R.string.home_bottom_nav)) },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.home_24px),
                                        contentDescription = stringResource(R.string.home_bottom_nav)
                                    )
                                }
                            )
                            NavigationBarItem(
                                selected = currentBackStackEntry?.destination.isRouteInHierarchy(
                                    NavRoute.About::class
                                ),
                                onClick = { navController.navigate(NavRoute.About) },
                                label = { Text(stringResource(R.string.about_bottom_nav)) },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.account_box_24px),
                                        contentDescription = stringResource(R.string.about_bottom_nav)
                                    )
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    TicketMasterNavHost(Modifier.padding(innerPadding), navController)
                }
            }
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false