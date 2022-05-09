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
    //Supply retrofit REST client to interact with API.
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