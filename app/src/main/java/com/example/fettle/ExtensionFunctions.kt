package com.example.fettle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


//Stackoverflow user 'Vince' (2019). LiveData remove Observer after first callback.
//Available from https://stackoverflow.com/questions/47854598/livedata-remove-observer-after-first-callback
//[accessed 24 March 2022].

//Stops both reading data from both local cache and requesting more from spoonacular happening at the same time.
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}