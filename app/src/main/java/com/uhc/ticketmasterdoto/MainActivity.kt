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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.uhc.lib.compose.utils.R
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import com.uhc.ticketmasterdoto.navigation.NavRoute
import com.uhc.ticketmasterdoto.navigation.TicketMasterNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            TicketMasterTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentRoute == NavRoute.Home.value,
                                onClick = { navController.navigate(NavRoute.Home.value) },
                                label = { Text(stringResource(R.string.home_bottom_nav)) },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.home_24px),
                                        contentDescription = stringResource(R.string.home_bottom_nav)
                                    )
                                }
                            )
                            NavigationBarItem(
                                selected = currentRoute == NavRoute.About.value,
                                onClick = { navController.navigate(NavRoute.About.value) },
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