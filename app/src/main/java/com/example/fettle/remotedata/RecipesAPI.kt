package com.example.fettle.remotedata

import com.example.fettle.modelClasses.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

//GET requests for API.
interface RecipesAPI {

    //Get recipes from spoonacular using retrofit.
    //The HTTP response will be instances of FoodRecipe class.
    @GET("/recipes/complexSearch")
    suspend fun get(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    //Same as above but only used by the search bar function.
    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(@QueryMap search: Map<String, String>): Response<FoodRecipe>
}