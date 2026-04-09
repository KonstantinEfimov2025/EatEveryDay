package com.example.eateveryday.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eateveryday.models.Meal
import com.example.eateveryday.network.RetrofitInstance
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchResults = mutableStateOf<List<Meal>>(emptyList())
    val searchResults: State<List<Meal>> = _searchResults

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun searchMeals(query: String) {
        viewModelScope.launch {
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
}