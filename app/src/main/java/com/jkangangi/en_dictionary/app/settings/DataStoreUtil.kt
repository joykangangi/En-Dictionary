package com.jkangangi.en_dictionary.app.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject

/**
 * this can be the util for various settings:
 *          - light/dark theme
 *          - fonts
 *          - languages etc
 */
class DataStoreUtil @Inject constructor (context: Context) {

    val dataStore = context.dataStore

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

        val THEME_KEY = booleanPreferencesKey("theme")
    }

}