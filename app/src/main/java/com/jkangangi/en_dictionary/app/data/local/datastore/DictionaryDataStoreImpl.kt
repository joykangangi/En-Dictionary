package com.jkangangi.en_dictionary.app.data.local.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DictionaryDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DictionaryDataStore {
    private val tag = this::class.java.simpleName

    override fun getData(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e(tag, "Error reading preferences.", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key]
            }
    }

    override suspend fun setData(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}