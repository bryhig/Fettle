package com.example.fettle

//Defines the three different types of network status, Success, Error and Lodaing.
//Used to display error messages and shimmer recycler view.
sealed class NetworkStatus<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : NetworkStatus<T>(data)
    class Error<T>(message: String?, data: T? = null) : NetworkStatus<T>(data, message)
    class Loading<T> : NetworkStatus<T>()
}

