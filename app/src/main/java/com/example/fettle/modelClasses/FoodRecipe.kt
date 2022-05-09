package com.example.fettle.modelClasses


import com.google.gson.annotations.SerializedName

//Food recipe class which defines the properties of a recipe obtained from spoonacular.
//Has one attribute results, which is a culmination of properties returned from API response.
data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>
)