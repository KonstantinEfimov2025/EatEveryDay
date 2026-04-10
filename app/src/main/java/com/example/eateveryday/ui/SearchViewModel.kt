package com.example.eateveryday.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eateveryday.models.Meal
import com.example.eateveryday.network.RetrofitInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchResults = mutableStateOf<List<Meal>>(emptyList())
    val searchResults: State<List<Meal>> = _searchResults

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _selectedMeal = mutableStateOf<Meal?>(null)
    val selectedMeal: State<Meal?> = _selectedMeal

    private var searchJob: Job? = null

    fun searchMeals(query: String) {
        if (query.length < 3) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.searchMeals(query)
                _searchResults.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadMealDetails(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getMealDetails(id)
                _selectedMeal.value = response.meals?.firstOrNull()
            } catch (e: Exception) {
                Log.e("SearchVM", "Ошибка загрузки деталей: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}