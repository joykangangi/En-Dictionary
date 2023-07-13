package com.jkangangi.en_dictionary.app.di

import com.jkangangi.en_dictionary.app.data.service.DictionaryService
import com.jkangangi.en_dictionary.app.data.service.DictionaryServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindsDictServiceImpl(dictionaryServiceImpl: DictionaryServiceImpl): DictionaryService

}