package com.example.eateveryday.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.eateveryday.models.EdamamRecipe
import com.example.eateveryday.navigation.Screen

@Composable
fun DiaryScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Diary")
    }
}

@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel) {
    var query by remember { mutableStateOf("") }
    val recipes by viewModel.searchResults
    val isLoading by viewModel.isLoading

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (it.length > 2) viewModel.searchMeals(it)
            },
            label = { Text("Search Recipes") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(recipes) { recipe ->
                    RecipeItem(recipe) {
                        viewModel.selectRecipe(recipe)
                        navController.navigate(Screen.Details.route)
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: EdamamRecipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(recipe.label, style = MaterialTheme.typography.titleMedium)
                Text("${recipe.calories.toInt()} kcal", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(navController: NavHostController, viewModel: SearchViewModel) {
    val recipe by viewModel.selectedRecipe

    recipe?.let { r ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(r.label) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Text("<")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier.padding(padding).verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = r.image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(16.dp)) {
                    Text("Ingredients", style = MaterialTheme.typography.headlineSmall)
                    r.ingredientLines.forEach { line ->
                        Text("• $line", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun RandomMealScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Random")
    }
}