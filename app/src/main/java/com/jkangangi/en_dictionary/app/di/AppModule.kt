package com.jkangangi.en_dictionary.app.di

import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepository
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.data.remote.network.KtorHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(client: KtorHttpClient): HttpClient {
       return client.getHttpClient()
    }


    @Provides
    @Singleton
    fun provideDictionaryRepository(impl: DictionaryRepositoryImpl): DictionaryRepository {
        return impl
    }
}