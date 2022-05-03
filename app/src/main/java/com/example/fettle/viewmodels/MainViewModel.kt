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

    //ROOM LOCAL DATA
    var readRecipes: LiveData<List<Entity>> = repository.local.readData().asLiveData()

    private fun writeRecipes(entity: Entity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.writeData(entity)

    }


    //RETROFIT REMOTE DATA
    var APIresponse: MutableLiveData<NetworkStatus<FoodRecipe>> = MutableLiveData()
    var searchResponse: MutableLiveData<NetworkStatus<FoodRecipe>> = MutableLiveData()

    fun get(queries: Map<String, String>) = viewModelScope.launch {
        getSafe(queries)
    }
    fun searchRecipe(search : Map<String, String>) = viewModelScope.launch {
        searchRecipeSafeCall(search)
    }

    private suspend fun searchRecipeSafeCall(search: Map<String, String>) {
        searchResponse.value = NetworkStatus.Loading()
        if (internet()) {
            try {
                val response = repository.remote.searchRecipe(search)
                searchResponse.value = handleResponse(response)
            } catch (error: Exception) {
                searchResponse.value = NetworkStatus.Error("NO INTERNET CONNECTION")
            }

        } else {
            searchResponse.value = NetworkStatus.Error("INTERNET CONNECTION UNAVAILABLE")
        }
    }

    private suspend fun getSafe(queries: Map<String, String>) {
        APIresponse.value = NetworkStatus.Loading()
        if (internet()) {
            try {
                val response = repository.remote.getRecipes(queries)
                APIresponse.value = handleResponse(response)
                val recipes = APIresponse.value!!.data
                if(recipes != null){
                    cache(recipes)
                }
            } catch (error: Exception) {
                APIresponse.value = NetworkStatus.Error("NO INTERNET CONNECTION")
            }

        } else {
            APIresponse.value = NetworkStatus.Error("INTERNET CONNECTION UNAVAILABLE")
        }
    }

    private fun cache(recipes: FoodRecipe) {
        val entity = Entity(recipes)
        writeRecipes(entity)
    }

    private fun handleResponse(response: Response<FoodRecipe>): NetworkStatus<FoodRecipe> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkStatus.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkStatus.Error("API KEY HAS EXPIRED")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkStatus.Error("NO RECIPES FOUND")
            }
            response.isSuccessful -> {
                val recipes = response.body()
                return NetworkStatus.Success(recipes!!)
            }
            else -> {
                return NetworkStatus.Error(response.message())
            }
        }

    }

    private fun internet(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false

        }
    }
}