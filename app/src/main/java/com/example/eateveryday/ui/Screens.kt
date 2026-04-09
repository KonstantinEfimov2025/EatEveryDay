package com.example.eateveryday.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eateveryday.models.Meal
import androidx.compose.runtime.getValue

@Composable
fun DiaryScreen() {
    Text(text = "Здесь будет список съеденного")
}

@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    val meals by viewModel.searchResults
    val isLoading by viewModel.isLoading

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (it.length > 2) viewModel.searchMeals(it)
            },
            label = { Text("Поиск блюда (на англ.)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(meals) { meal ->
                    Text(
                        text = meal.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun RandomMealScreen() {
    Text(text = "Здесь будет случайное блюдо")
}

@Composable
fun MealDetailScreen(mealId: String?) {
    Text(text = "Детали блюда с ID: $mealId")
}