package com.jkangangi.en_dictionary.app.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DictionaryDataStore {

    fun getData(key: Preferences.Key<String>): Flow<String?>

    suspend fun setData(key: Preferences.Key<String>, value: String)

}