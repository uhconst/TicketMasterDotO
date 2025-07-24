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
import androidx.navigation.compose.rememberNavController
import com.uhc.ticketmasterdoto.navigation.NavigationStack
import com.uhc.ticketmasterdoto.navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = false,
                            onClick = { navController.navigate(Routes.Main) },
                            label = { Text("Home") },
                            icon = { Icon(Icons.Default.Home, contentDescription = null) }
                        )
                        // Add more items as needed
                    }
                }
            ) { innerPadding ->
                NavigationStack(Modifier.padding(innerPadding))
            }

    /*        Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavigationStack()
            }*/
        }
    }
}