package com.jkangangi.en_dictionary.app.di

import android.app.Application
import androidx.room.Room
import com.jkangangi.en_dictionary.app.data.local.WordDatabase
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryAPI
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
    //viewmodelcomponent
    fun provideDictionaryRepository(impl: DictionaryRepositoryImpl): DictionaryAPI {
        return impl
    }

    //Word-Local DB
    @Provides
    @Singleton
    fun providesDatabase(application: Application): WordDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = WordDatabase::class.java,
            name = WordDatabase::class.simpleName
        ).build()
    }
}