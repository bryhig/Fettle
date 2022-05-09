package com.example.fettle.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//Room database itself.
@Database(
    entities = [Entity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class Database : RoomDatabase(){
    abstract fun DAO() : DAO
}