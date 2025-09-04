package com.example.memora.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

private val SORT_ORDER_KEY = booleanPreferencesKey("sort_order")
private val THEME_KEY = intPreferencesKey("theme_key")

class PreferencesManager(private val context: Context) {

    val sortOrderFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[SORT_ORDER_KEY] == true }

    suspend fun saveSortOrder(isSorted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SORT_ORDER_KEY] = isSorted
        }
    }


    val selectedThemeFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[THEME_KEY] ?: 0 }

    suspend fun saveTheme(themeIndex: Int) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = themeIndex
        }
    }
}