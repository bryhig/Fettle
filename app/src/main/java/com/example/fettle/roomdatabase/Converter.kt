package com.example.fettle.roomdatabase

import androidx.room.TypeConverter
import com.example.fettle.modelClasses.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    var gson = Gson()

    @TypeConverter
    //Convert FoodRecipe instance back into JSON to be stored in database.
    fun recipeToJson(recipe: FoodRecipe): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    //Convert JSON format stored in database to instance of FoodRecipe class.
    fun toRecipe(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }
}