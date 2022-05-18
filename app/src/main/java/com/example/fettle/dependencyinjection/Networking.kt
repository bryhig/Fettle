package com.example.fettle.dependencyinjection

import com.example.fettle.remotedata.RecipesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.fettle.Global.Companion.API_URL
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
/* Google (2022) Dependency Injection with Hilt. Mountain View, USA: Google
* Available from https://developer.android.com/training/dependency-injection/hilt-android
* [accessed 15 March 2022]*/

@Module
@InstallIn(SingletonComponent::class)
//General networking functions.
object Networking {
    @Singleton
    @Provides
    //Supply HTTP client to application.
    fun supplyHTTP(): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    //GSON converter. Used to parse API response into object.
    fun supplyConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    //Supply retrofit to allow the app to get dara fromm the spoonacular API.
    //Gson converter factory used to convert gson response.
    fun supplyRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(API_URL).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun supplyAPI(retrofit: Retrofit): RecipesAPI {
        return retrofit.create(RecipesAPI::class.java)
    }
}