package com.example.eateveryday.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Splash : Screen("splash_screen", "Welcome", Icons.Default.Info)
    object Diary : Screen("diary_screen", "Diary", Icons.Default.DateRange)
    object Search : Screen("search_screen", "Search", Icons.Default.Search)
    object Random : Screen("random_screen", "Random", Icons.Default.Refresh)
    object Details : Screen("details_screen", "Details", Icons.Default.Info)
}