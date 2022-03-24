package com.example.fettle.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun writeData(entity: Entity)
    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    fun readData() : Flow<List<Entity>>
}