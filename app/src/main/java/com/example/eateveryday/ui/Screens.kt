package com.example.eateveryday.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DiaryScreen() {
    Text(text = "Здесь будет список съеденного")
}

@Composable
fun SearchScreen() {
    Text(text = "Здесь будет поиск блюд")
}

@Composable
fun RandomMealScreen() {
    Text(text = "Здесь будет случайное блюдо")
}

@Composable
fun MealDetailScreen(mealId: String?) {
    Text(text = "Детали блюда с ID: $mealId")
}