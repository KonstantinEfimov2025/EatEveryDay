package com.example.eateveryday.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
// Явно импортируем каждую функцию, чтобы IDE не "терялась"
import com.example.eateveryday.ui.DiaryScreen
import com.example.eateveryday.ui.SearchScreen
import com.example.eateveryday.ui.RandomMealScreen
import com.example.eateveryday.ui.MealDetailScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Diary.route
    ) {
        composable(route = Screen.Diary.route) {
            DiaryScreen()
        }
        composable(route = Screen.Search.route) {
            // Здесь SearchScreen подхватит свою ViewModel автоматически
            SearchScreen()
        }
        composable(route = Screen.Random.route) {
            RandomMealScreen()
        }
        composable(route = Screen.Details.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("mealId")
            MealDetailScreen(mealId = id)
        }
    }
}