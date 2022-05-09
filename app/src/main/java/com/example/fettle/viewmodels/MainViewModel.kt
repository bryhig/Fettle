package com.example.fettle.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.fettle.NetworkStatus
import com.example.fettle.modelClasses.FoodRecipe
import com.example.fettle.remotedata.Repository
import com.example.fettle.roomdatabase.Entity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    //ROOM LOCAL DATA - read the local data.
    var readRecipes: LiveData<List<Entity>> = repository.local.readData().asLiveData()

    //Write the data into a database entity.
    private fun writeRecipes(entity: Entity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.writeData(entity)
    }


    //RETROFIT REMOTE DATA
    var APIresponse: MutableLiveData<NetworkStatus<FoodRecipe>> = MutableLiveData()
    var searchResponse: MutableLiveData<NetworkStatus<FoodRecipe>> = MutableLiveData()


    fun get(queries: Map<String, String>) = viewModelScope.launch {
        getSafe(queries)
    }

    fun searchRecipe(search: Map<String, String>) = viewModelScope.launch {
        searchRecipeSafeCall(search)
    }

    private suspend fun searchRecipeSafeCall(search: Map<String, String>) {
        searchResponse.value = NetworkStatus.Loading()
        //If there is internet search for recipes.
        if (internet()) {
            try {
                val response = repository.remote.searchRecipe(search)
                searchResponse.value = handleResponse(response)
                //If there is not internet set error message.
            } catch (error: Exception) {
                searchResponse.value = NetworkStatus.Error("NO INTERNET CONNECTION")
            }
            //Basically the same as the catch block.
        } else {
            searchResponse.value = NetworkStatus.Error("INTERNET CONNECTION UNAVAILABLE")
        }
    }

    //Get recipes from spoonacular.
    private suspend fun getSafe(queries: Map<String, String>) {
        APIresponse.value = NetworkStatus.Loading()
        if (internet()) {
            try {
                //GET request spoonacular for recipes based on the query.
                val response = repository.remote.getRecipes(queries)
                APIresponse.value = handleResponse(response)
                val recipes = APIresponse.value!!.data
                //Store the recipes in the local ROOM database.
                if (recipes != null) {
                    cache(recipes)
                }
                //Catch if there is no internet connection.
            } catch (error: Exception) {
                APIresponse.value = NetworkStatus.Error("NO INTERNET CONNECTION")
            }
            //Catch network error.
        } else {
            APIresponse.value = NetworkStatus.Error("INTERNET CONNECTION UNAVAILABLE")
        }
    }

    //Write a recipe into the local ROOM database.
    private fun cache(recipes: FoodRecipe) {
        val entity = Entity(recipes)
        writeRecipes(entity)
    }

    //Handle the response from spoonacular.
    private fun handleResponse(response: Response<FoodRecipe>): NetworkStatus<FoodRecipe> {
        when {
            //Handle network timeout.
            response.message().toString().contains("timeout") -> {
                return NetworkStatus.Error("Timeout")
            }
            //Spoonacular responds with 402 if API key is invalid. If this happens please contact me so i can
            //provide a new one. API key varible is within GLobal.kt
            response.code() == 402 -> {
                return NetworkStatus.Error("API KEY HAS EXPIRED")
            }
            //Handle if API response is empty.
            response.body()!!.results.isEmpty() -> {
                return NetworkStatus.Error("NO RECIPES BY QUERY")
            }
            //If successful store the response in recipes.
            response.isSuccessful -> {
                val recipes = response.body()
                return NetworkStatus.Success(recipes!!)
            }
            else -> {
                return NetworkStatus.Error(response.message())
            }
        }

    }

    //Set up network capabilities of app and check for internet connection
    private fun internet(): Boolean {
        //Set up system service.
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        //Set up allowed connectivity types.
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false

        }
    }
}