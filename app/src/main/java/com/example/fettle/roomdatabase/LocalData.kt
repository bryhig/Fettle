package com.example.fettle.roomdatabase

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalData @Inject constructor(
    private val dao: DAO
) {
    suspend fun writeData(entity : Entity){
        dao.writeData(entity)
    }

    fun readData() : Flow<List<Entity>>{
        return dao.readData()
    }
}