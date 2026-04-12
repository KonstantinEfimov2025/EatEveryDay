package com.example.eateveryday.network

import com.example.eateveryday.models.EdamamResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MealApi {
    @GET("api/recipes/v2")
    suspend fun searchMeals(
        @Query("q") query: String,
        @Query("type") type: String = "public",
        @Query("app_id") appId: String = "06a14cea",
        @Query("app_key") appKey: String = "58385588ca7271a643169288240907cd",
        @Header("Edamam-Account-User") userId: String = "06a14cea"
    ): EdamamResponse
}