package com.example.eateveryday

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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eateveryday.navigation.SetupNavGraph
import com.example.eateveryday.navigation.Screen // ВАЖНО
import com.example.eateveryday.ui.theme.EatEveryDayTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatEveryDayTheme {
                val navController = rememberNavController()
                val items = listOf(Screen.Diary, Screen.Search, Screen.Random)

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            items.forEach { screen ->
                                NavigationBarItem(
                                    icon = {
                                        when(screen) {
                                            Screen.Diary -> Icon(Icons.Default.DateRange, contentDescription = null)
                                            Screen.Search -> Icon(Icons.Default.Search, contentDescription = null)
                                            Screen.Random -> Icon(Icons.Default.Star, contentDescription = null)
                                            else -> Icon(Icons.Default.Search, contentDescription = null)
                                        }
                                    },
                                    label = { Text(screen.route.replaceFirstChar { it.uppercase() }) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}