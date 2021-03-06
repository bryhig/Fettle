package com.example.fettle

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("fettle_preferences")

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedDishType = stringPreferencesKey("dishType")
        val selectedDishTypeID = intPreferencesKey("dishTypeID")
        val selectedDietType = stringPreferencesKey("dietType")
        val selectedDietTypeID = intPreferencesKey("dietTypeID")
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    //Saves the types selected on the filter bottom sheet based on what the user has selected.
    suspend fun saveTypes(dishType: String, dishTypeID: Int, dietType: String, dietTypeID: Int) {
        dataStore.edit { preferences -> preferences[PreferenceKeys.selectedDishType] = dishType }
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDishTypeID] = dishTypeID
        }
        dataStore.edit { preferences -> preferences[PreferenceKeys.selectedDietType] = dietType }
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDietTypeID] = dietTypeID
        }
    }

    val readTypes: Flow<DishAndDietType> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val selectedDishType = preferences[PreferenceKeys.selectedDishType] ?: "main course"
        val selectedDishTypeID = preferences[PreferenceKeys.selectedDishTypeID] ?: 0
        val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: "gluten free"
        val selectedDietTypeID = preferences[PreferenceKeys.selectedDietTypeID] ?: 0
        DishAndDietType(selectedDishType, selectedDishTypeID, selectedDietType, selectedDietTypeID)
    }
}

//Define dish and diet types class and its attributes
data class DishAndDietType(
    val selectedDishType: String,
    val selectedDishID: Int,
    val selectedDietType: String,
    val selectedDietID: Int
)