package com.example.fettle.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fettle.Global.Companion.TABLE
import com.example.fettle.modelClasses.FoodRecipe

@Entity(tableName = TABLE)
//Defines an entity within the ROOM database. Entity contains an instance of FoodRecipe.
class Entity(
    var recipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}