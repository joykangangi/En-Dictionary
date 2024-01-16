package com.jkangangi.en_dictionary.app.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * this can be the util for various settings:
 *          - light/dark theme
 *          - fonts
 *          - languages etc
 */

class UserPreferences @Inject constructor (private val dataStore: DataStore<Preferences>) {

    suspend fun setTheme(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkTheme
        }
    }

    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }
    companion object {
        val THEME_KEY = booleanPreferencesKey("theme")
    }

}