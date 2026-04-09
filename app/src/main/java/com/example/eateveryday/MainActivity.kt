package com.example.eateveryday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.eateveryday.navigation.Screen
import com.example.eateveryday.navigation.SetupNavGraph
import com.example.eateveryday.ui.theme.EatEveryDayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatEveryDayTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                    label = { Text("Дневник") },
                    selected = false, // Позже добавим логику выбора
                    onClick = { navController.navigate(Screen.Diary.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    label = { Text("Поиск") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Search.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Refresh, contentDescription = null) },
                    label = { Text("Рандом") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Random.route) }
                )
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            SetupNavGraph(navController = navController)
        }
    }
}