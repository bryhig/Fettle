package com.example.fettle

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Triggers dagger hilt component generation.
@HiltAndroidApp
class MyApplication : Application() {
}