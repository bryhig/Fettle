package com.example.fettle.remotedata

import com.example.fettle.modelClasses.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/* It is worth noting that this and other kotlin classes surrounding the spoonacular API, and how to GET request
* using retrofit, etc., was learnt from an online course I took a while ago.
*
* Udemy (2020) Food Recipe App - Android Development in Kotlin. [course]. Ireland: Udemy
* available from https://www.udemy.com/course/modern-food-recipes-app-android-development-with-kotlin
* [accessed 15 July 2021]. */


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