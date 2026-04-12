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
import com.example.eateveryday.models.DiaryEntry
import com.example.eateveryday.models.EdamamRecipe
import com.example.eateveryday.navigation.Screen

@Composable
fun DiaryScreen(viewModel: DiaryViewModel) {
    val items by viewModel.diaryItems.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Мой Дневник", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(16.dp))
        }
        items(items) { item ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(item.title, style = MaterialTheme.typography.titleMedium)
                        Text("${item.calories} ккал", style = MaterialTheme.typography.bodySmall)
                    }
                    IconButton(onClick = { viewModel.removeEntry(item) }) {
                        Text("✕")
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel) {
    var query by remember { mutableStateOf("") }
    val recipes by viewModel.searchResults

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (it.length > 2) viewModel.searchMeals(it)
            },
            label = { Text("Поиск рецептов") },
            modifier = Modifier.fillMaxWidth()
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    diaryViewModel: DiaryViewModel
) {
    val recipe by searchViewModel.selectedRecipe

    recipe?.let { r ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Детали") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) { Text("<") }
                    }
                )
            }
        ) { padding ->
            Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
                AsyncImage(
                    model = r.image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(250.dp),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(16.dp)) {
                    Text(r.label, style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            diaryViewModel.addEntry(
                                DiaryEntry(
                                    title = r.label,
                                    imageUrl = r.image,
                                    calories = r.calories.toInt()
                                )
                            )
                        }
                    ) {
                        Text("Добавить в дневник")
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Ингредиенты:", style = MaterialTheme.typography.titleLarge)
                    r.ingredientLines.forEach { Text("• $it", Modifier.padding(vertical = 2.dp)) }
                }
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: EdamamRecipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onClick() }
    ) {
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Text(recipe.label, style = MaterialTheme.typography.titleMedium)
        }
    }
}