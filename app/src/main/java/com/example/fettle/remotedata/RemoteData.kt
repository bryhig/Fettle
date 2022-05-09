package com.example.fettle.remotedata

import com.example.fettle.modelClasses.FoodRecipe
import com.example.fettle.remotedata.RecipesAPI
import retrofit2.Response
import javax.inject.Inject

class RemoteData @Inject constructor(private val recipesAPI: RecipesAPI) {
    //Get recipes from spoonacular by passing it queries to respond to.
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return recipesAPI.get(queries)
    }

    //Same as above but passes it the query entered into the search bar function.
    suspend fun searchRecipe(search: Map<String, String>): Response<FoodRecipe> {
        return recipesAPI.searchRecipes(search)
    }
}