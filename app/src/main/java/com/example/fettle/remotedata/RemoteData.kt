package com.example.fettle.remotedata

import com.example.fettle.modelClasses.FoodRecipe
import com.example.fettle.remotedata.RecipesAPI
import retrofit2.Response
import javax.inject.Inject

class RemoteData @Inject constructor(private val recipesAPI: RecipesAPI) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe>{
        return recipesAPI.get(queries)
    }
}