package com.example.fettle.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
/* This resource was used for code in this and other ROOM database kotlin files.
*
* Google (2022) Save data in local database using room. Mountain View, USA: Google.
* Available from https://developer.android.com/training/data-storage/room [accessed
* 21 March 2022]*/

//Room database itself.
@Database(
    entities = [Entity::class],
    version = 1,
    exportSchema = false
)
//Set up the type converter used to convert Json to FoodRecipe and vice-versa.
@TypeConverters(Converter::class)
abstract class Database : RoomDatabase(){
    abstract fun DAO() : DAO
}