package com.example.fettle.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
//Room database data access object.
interface DAO {
    //Write data to ROOM database. Replace everything on data conflict.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun writeData(entity: Entity)

    //SQL query used to read data from the ROOM database.
    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    fun readData(): Flow<List<Entity>>
}