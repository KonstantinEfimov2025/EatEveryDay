package com.example.eateveryday.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.eateveryday.models.DiaryEntry
import com.example.eateveryday.models.EdamamRecipe
import com.example.eateveryday.navigation.Screen

@Composable
fun DiaryScreen(viewModel: DiaryViewModel) {
    val items by viewModel.diaryItems.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Food Diary",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
        }

        if (items.isEmpty()) {
            item {
                Box(Modifier.fillParentMaxHeight(0.7f), contentAlignment = Alignment.Center) {
                    Text("Your diary is empty", color = MaterialTheme.colorScheme.outline)
                }
            }
        }

        items(items) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text(item.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text("${item.calories} kcal", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                    }
                    IconButton(onClick = { viewModel.removeEntry(item) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
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
            label = { Text("Search for recipes") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                    title = { Text("Recipe Details") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
                AsyncImage(
                    model = r.image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(20.dp)) {
                    Text(r.label, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text("${r.calories.toInt()} kcal", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        onClick = {
                            diaryViewModel.addEntry(
                                DiaryEntry(
                                    title = r.label,
                                    imageUrl = r.image,
                                    calories = r.calories.toInt()
                                )
                            )
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Add to Diary")
                    }
                    Spacer(Modifier.height(24.dp))
                    Text("Ingredients", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    r.ingredientLines.forEach { line ->
                        Text("• $line", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: EdamamRecipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Text(recipe.label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
        }
    }
}