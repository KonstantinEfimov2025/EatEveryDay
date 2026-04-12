package com.example.eateveryday.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eateveryday.ui.*

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val searchViewModel: SearchViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Diary.route
    ) {
        composable(route = Screen.Diary.route) {
            DiaryScreen()
        }
        composable(route = Screen.Search.route) {
            SearchScreen(navController, searchViewModel)
        }
        composable(route = Screen.Random.route) {
            RandomMealScreen()
        }
        composable(route = Screen.Details.route) {
            MealDetailScreen(navController, searchViewModel)
        }
    }
}