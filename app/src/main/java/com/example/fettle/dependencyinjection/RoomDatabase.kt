package com.example.fettle.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.fettle.Global.Companion.NAME
import com.example.fettle.roomdatabase.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabase {
    @Singleton
    @Provides
    fun supplyDatabase(
        @ApplicationContext context : Context
    )= Room.databaseBuilder(
        context,
        Database::class.java,
        NAME
    ).build()

    @Singleton
    @Provides
    fun supplyDAO(database: Database) = database.DAO()

}