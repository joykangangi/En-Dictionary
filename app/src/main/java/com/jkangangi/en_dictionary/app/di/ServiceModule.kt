package com.jkangangi.en_dictionary.app.di

import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.data.service.DictionaryService
import com.jkangangi.en_dictionary.app.data.service.DictionaryServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ServiceModule {

    @Binds
    @Singleton
    fun bindsDictServiceImpl(dictionaryServiceImpl: DictionaryServiceImpl): DictionaryService

    @Binds
    @Singleton
    fun bindsDictionaryRepositoryImpl(dictionaryRepositoryImpl: DictionaryRepositoryImpl): DictionaryRepository

}