package com.jkangangi.en_dictionary.app.di

import com.jkangangi.en_dictionary.app.data.local.datastore.DictionaryDataStore
import com.jkangangi.en_dictionary.app.data.local.datastore.DictionaryDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindUserPreferences(
        dataStoreImpl: DictionaryDataStoreImpl
    ): DictionaryDataStore
}