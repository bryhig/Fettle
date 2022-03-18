package com.example.fettle

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fettle.modelClasses.FoodRecipe
import com.example.fettle.remotedata.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    private val Repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var APIresponse: MutableLiveData<NetworkStatus<FoodRecipe>> = MutableLiveData()

    fun get(queries: Map<String, String>) = viewModelScope.launch {
        getSafe(queries)
    }

    private suspend fun getSafe(queries: Map<String, String>) {
        APIresponse.value = NetworkStatus.Loading()
        if (internet()) {
            try {
                val response = Repository.remote.getRecipes(queries)
                APIresponse.value = handleResponse(response)
            } catch (error: Exception) {
                APIresponse.value = NetworkStatus.Error("NO RECIPE FOUND")
            }

        } else {
            APIresponse.value = NetworkStatus.Error("INTERNET CONNECTION UNAVAILABLE")
        }
    }

    private fun handleResponse(response: Response<FoodRecipe>): NetworkStatus<FoodRecipe>? {
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