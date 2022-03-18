package com.example.fettle.remotedata

import com.example.fettle.modelClasses.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RecipesAPI {

    @GET("/recipes/complexSearch")
    suspend fun get(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>
}