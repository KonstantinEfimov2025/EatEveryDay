package com.example.eateveryday.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eateveryday.models.EdamamRecipe
import com.example.eateveryday.network.RetrofitInstance
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchResults = mutableStateOf<List<EdamamRecipe>>(emptyList())
    val searchResults: State<List<EdamamRecipe>> = _searchResults

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _selectedRecipe = mutableStateOf<EdamamRecipe?>(null)
    val selectedRecipe: State<EdamamRecipe?> = _selectedRecipe

    fun searchMeals(query: String) {
        if (query.length < 3) return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.searchMeals(query)
                _searchResults.value = response.hits.map { it.recipe }
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectRecipe(recipe: EdamamRecipe) {
        _selectedRecipe.value = recipe
    }
}