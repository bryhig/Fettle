package com.example.fettle

class Global {
    companion object{
        //API key and URL
        const val API_KEY = "4c3a2719faa74767b6abb3438ac2351b"
        const val API_URL = "https://api.spoonacular.com"
        //Parameters used to query spoonacular and get recipes.
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_RECIPE_INFO = "addRecipeInformation"
        const val QUERY_INGREDIENTS = "fillIngredients"
        //ROOM database
        const val NAME = "local_data"
        const val TABLE = "recipe_table"
        //Bottom sheet
        const val DEFAULT_COURSE = "main course"
        const val DEFAULT_DIET = "gluten free"
        const val DEFAULT_NUMBER = "50"
    }
}