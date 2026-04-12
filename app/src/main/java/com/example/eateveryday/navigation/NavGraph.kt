package com.example.eateveryday.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.launch

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val searchViewModel: SearchViewModel = viewModel()
    val diaryViewModel: DiaryViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
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
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val fetchRandomRecipe = {
        scope.launch {
            isRefreshing = true
            try {
                val queries = listOf("Chicken", "Salad", "Pasta", "Fish", "Beef", "Soup")
                val response = RetrofitInstance.api.getRandomMeal(query = queries.random())
                recipe = response.hits.randomOrNull()?.recipe
            } catch (e: Exception) {
                recipe = null
            } finally {
                isRefreshing = false
            }
        }
    }

    LaunchedEffect(Unit) { fetchRandomRecipe() }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isRefreshing) {
            CircularProgressIndicator()
        } else {
            recipe?.let { r ->
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                    Text("Random Idea", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(24.dp))
                    AsyncImage(
                        model = r.image,
                        contentDescription = null,
                        modifier = Modifier.size(280.dp).clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(r.label, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(32.dp))
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
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Add to Diary") }
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { fetchRandomRecipe() },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("New Recipe") }
                }
            } ?: Text("Failed to load recipe. Try again.")
        }
    }
}