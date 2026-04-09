package com.example.eateveryday.models

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strInstructions") val instructions: String,
    @SerializedName("strMealThumb") val imageUrl: String,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strArea") val area: String?
)

data class MealResponse(
    val meals: List<Meal>?
)