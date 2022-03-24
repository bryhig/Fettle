package com.example.fettle.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fettle.Global.Companion.TABLE
import com.example.fettle.modelClasses.FoodRecipe

@Entity(tableName = TABLE)
class Entity(
    var recipe : FoodRecipe
){
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}