package com.example.eateveryday.models

import com.google.gson.annotations.SerializedName

data class EdamamResponse(
    @SerializedName("hits") val hits: List<Hit>
)

data class Hit(
    @SerializedName("recipe") val recipe: EdamamRecipe
)

data class EdamamRecipe(
    @SerializedName("label") val label: String,
    @SerializedName("image") val image: String,
    @SerializedName("url") val url: String,
    @SerializedName("ingredientLines") val ingredientLines: List<String>,
    @SerializedName("calories") val calories: Double
)