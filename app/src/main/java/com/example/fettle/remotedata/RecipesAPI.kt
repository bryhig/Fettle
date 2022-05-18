package com.example.fettle.remotedata

import com.example.fettle.modelClasses.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/* It is worth noting that this and other kotlin classes surrounding the spoonacular API, and how to GET request
* using retrofit, etc., was learnt from an online courses.
*
* YouTube user Smartherd (2018) Android Retrofit Using Kotlin. YouTube.
* Available from https://www.youtube.com/playlist?list=PLlxmoA0rQ-LzEmWs4T99j2w6VnaQVGEtR
* [accessed 17 March 2022].
*
* Udemy (2020) Food Recipe App - Android Development in Kotlin. [course]. Ireland: Udemy.
* Available from https://www.udemy.com/course/modern-food-recipes-app-android-development-with-kotlin
* [accessed 15 July 2021]. */




//GET requests for API.
interface RecipesAPI {

    //Get recipes from spoonacular using retrofit.
    //The JSON response will be instances of FoodRecipe class.
    @GET("/recipes/complexSearch")
    suspend fun get(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    //Same as above but only used by the search bar function.
    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(@QueryMap search: Map<String, String>): Response<FoodRecipe>
}