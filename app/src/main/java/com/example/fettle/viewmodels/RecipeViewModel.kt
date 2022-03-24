package com.example.fettle.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.fettle.Global
import com.example.fettle.Global.Companion.API_KEY
import com.example.fettle.Global.Companion.QUERY_API_KEY
import com.example.fettle.Global.Companion.QUERY_DIET
import com.example.fettle.Global.Companion.QUERY_INGREDIENTS
import com.example.fettle.Global.Companion.QUERY_NUMBER
import com.example.fettle.Global.Companion.QUERY_RECIPE_INFO
import com.example.fettle.Global.Companion.QUERY_TYPE

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    fun getQueries() : HashMap<String, String>{
        val queries : HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_RECIPE_INFO] = "true"
        queries[QUERY_INGREDIENTS] = "true"
        return queries
    }
}