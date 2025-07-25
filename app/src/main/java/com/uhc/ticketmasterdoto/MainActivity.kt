package com.uhc.ticketmasterdoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.uhc.lib.compose.utils.R
import com.uhc.lib.compose.utils.theme.TicketMasterTheme
import com.uhc.ticketmasterdoto.navigation.TicketMasterNavHost
import com.uhc.ticketmasterdoto.navigation.NavRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            TicketMasterTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = false,
                                onClick = { navController.navigate(NavRoute.Home.value) },
                                label = { Text(stringResource(R.string.home_bottom_nav)) },
                                icon = { Icon(Icons.Default.Home, contentDescription = null) }
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