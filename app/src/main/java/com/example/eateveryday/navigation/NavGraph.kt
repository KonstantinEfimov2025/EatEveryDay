package com.example.eateveryday.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.example.eateveryday.models.DiaryEntry
import com.example.eateveryday.models.EdamamRecipe
import com.example.eateveryday.network.RetrofitInstance
import com.example.eateveryday.ui.*

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val searchViewModel: SearchViewModel = viewModel()
    val diaryViewModel: DiaryViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Diary.route
    ) {
        composable(route = Screen.Diary.route) {
            DiaryScreen(viewModel = diaryViewModel)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }
        composable(route = Screen.Random.route) {
            RandomMealScreen(diaryViewModel = diaryViewModel)
        }
        composable(route = Screen.Details.route) {
            MealDetailScreen(
                navController = navController,
                searchViewModel = searchViewModel,
                diaryViewModel = diaryViewModel
            )
        }
    }
}

@Composable
fun RandomMealScreen(diaryViewModel: DiaryViewModel) {
    var recipe by remember { mutableStateOf<EdamamRecipe?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getRandomMeal(
                query = listOf("Chicken", "Salad", "Pasta").random()
            )
            recipe = response.hits.randomOrNull()?.recipe
        } catch (e: Exception) {
            recipe = null
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        recipe?.let { r ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Случайное блюдо", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(16.dp))
                AsyncImage(
                    model = r.image,
                    contentDescription = null,
                    modifier = Modifier.size(250.dp).clip(RoundedCornerShape(16.dp))
                )
                Spacer(Modifier.height(16.dp))
                Text(r.label, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        diaryViewModel.addEntry(
                            DiaryEntry(
                                title = r.label,
                                imageUrl = r.image,
                                calories = r.calories.toInt()
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Добавить в дневник")
                }
            }
        } ?: CircularProgressIndicator()
    }
}