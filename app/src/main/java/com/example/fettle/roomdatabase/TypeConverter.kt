package com.example.fettle.roomdatabase

import androidx.room.TypeConverter
import com.example.fettle.modelClasses.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    var gson = Gson()
    @TypeConverter
    fun recipeToString(recipe : FoodRecipe) : String{
        return gson.toJson(recipe)
    }
    @TypeConverter
    fun toRecipe(data : String) : FoodRecipe{
        val listType = object : TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data, listType)
    }
}