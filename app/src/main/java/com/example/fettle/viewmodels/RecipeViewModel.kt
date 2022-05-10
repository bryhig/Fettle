package com.example.fettle.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fettle.DataStoreRepository
import com.example.fettle.DishAndDietType
import com.example.fettle.Global.Companion.API_KEY
import com.example.fettle.Global.Companion.DEFAULT_COURSE
import com.example.fettle.Global.Companion.DEFAULT_DIET
import com.example.fettle.Global.Companion.DEFAULT_NUMBER
import com.example.fettle.Global.Companion.QUERY_API_KEY
import com.example.fettle.Global.Companion.QUERY_DIET
import com.example.fettle.Global.Companion.QUERY_INGREDIENTS
import com.example.fettle.Global.Companion.QUERY_NUMBER
import com.example.fettle.Global.Companion.QUERY_RECIPE_INFO
import com.example.fettle.Global.Companion.QUERY_SEARCH
import com.example.fettle.Global.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    //Used by default
    private var meal = "main course"
    private var diet = "gluten free"

    val readTypes = dataStoreRepository.readTypes

    //Used on the filter bottom sheet to save the types the user has selected.
    //Calls function by same name in DataStoreRepository class
    fun saveTypes(meal: String, id: Int, diet: String, dietID: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveTypes(meal, id, diet, dietID)
        }

    //Set up queries with all appropriate information.
    fun getQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readTypes.collect { value ->
                meal = value.selectedDishType
                diet = value.selectedDietType
            }
        }
        queries[QUERY_NUMBER] = DEFAULT_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = meal
        queries[QUERY_DIET] = diet
        queries[QUERY_RECIPE_INFO] = "true"
        queries[QUERY_INGREDIENTS] = "true"
        return queries
    }

    //Used for the searchbar functionality.
    fun applySearchQuery(search: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = search
        queries[QUERY_NUMBER] = DEFAULT_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_RECIPE_INFO] = "true"
        queries[QUERY_INGREDIENTS] = "true"
        return queries

    }
}