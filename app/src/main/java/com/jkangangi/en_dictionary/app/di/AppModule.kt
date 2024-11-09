package com.jkangangi.en_dictionary.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.jkangangi.en_dictionary.app.data.local.datastore.DictionaryDataStore
import com.jkangangi.en_dictionary.app.data.local.datastore.DictionaryDataStoreImpl
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryDao
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryDatabase
import com.jkangangi.en_dictionary.app.data.local.room.MIGRATION_3_4
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.data.service.DictionaryService
import com.jkangangi.en_dictionary.app.data.service.DictionaryServiceImpl

private const val USER_PREFERENCES = "user_preferences"

interface AppModule {
    val dictionaryDatabase: DictionaryDatabase
    val dictionaryDao: DictionaryDao
    val dataStore: DataStore<Preferences>
    val dictionaryDataStore: DictionaryDataStore
    val dictionaryService: DictionaryService
    val dictionaryRepository: DictionaryRepository
}

class AppModuleImpl(
    private val appContext: Context,
) : AppModule {

    override val dictionaryDatabase: DictionaryDatabase by lazy {
        Room.databaseBuilder(
            context = appContext,
            klass = DictionaryDatabase::class.java,
            name = DictionaryDatabase::class.simpleName
        )
            .addMigrations(MIGRATION_3_4)
            .build()
    }

    override val dictionaryDao: DictionaryDao by lazy {
        dictionaryDatabase.dictionaryDao()
    }

    override val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile(name = USER_PREFERENCES)
            }
        )
    }

    override val dictionaryDataStore: DictionaryDataStore by lazy {
        DictionaryDataStoreImpl(dataStore)
    }

    override val dictionaryService: DictionaryService by lazy {
        DictionaryServiceImpl()
    }

    override val dictionaryRepository: DictionaryRepository by lazy {
        DictionaryRepositoryImpl(
            dao = dictionaryDao,
            dictionaryService = dictionaryService
        )
    }
}