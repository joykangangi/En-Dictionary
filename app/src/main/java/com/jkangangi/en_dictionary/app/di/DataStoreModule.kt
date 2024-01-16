package com.jkangangi.en_dictionary.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jkangangi.en_dictionary.app.data.repository.UserPreferenceRepository
import com.jkangangi.en_dictionary.app.data.repository.UserPreferenceRepositoryImpl
import com.jkangangi.en_dictionary.app.settings.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module

object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(name = "USER_PREFERENCES")
            }
        )
    }

    @Provides
    @Singleton
    fun provideUserPreference(dataStore: DataStore<Preferences>): UserPreferences {
        return UserPreferences(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(userPreferences: UserPreferences): UserPreferenceRepository {

        return UserPreferenceRepositoryImpl(preferences = userPreferences)
    }
}