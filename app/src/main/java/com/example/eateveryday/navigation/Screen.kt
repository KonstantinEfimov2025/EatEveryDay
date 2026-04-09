package com.example.eateveryday.navigation

sealed class Screen(val route: String) {
    object Diary : Screen("diary")
    object Search : Screen("search")
    object Random : Screen("random")
    object Details : Screen("details/{mealId}") {
        fun passId(id: String) = "details/$id"
    }
}