package com.jkangangi.en_dictionary.app.di

import android.app.Application
import androidx.room.Room
import com.jkangangi.en_dictionary.app.data.local.WordDao
import com.jkangangi.en_dictionary.app.data.local.WordDatabase
import com.jkangangi.en_dictionary.app.data.remote.dto.DictionaryServiceImpl
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepository
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepositoryImpl
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
    fun providesDatabase(application: Application): WordDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = WordDatabase::class.java,
            name = WordDatabase::class.simpleName
        ).build()
    }

    @Provides
    @Singleton
    fun providesDictionaryRepository(
        wordDatabase: WordDatabase,
        dictionaryService: DictionaryServiceImpl
    ): DictionaryRepository {
        return DictionaryRepositoryImpl(
            dao = wordDatabase.wordDao(),
            dictionaryService = dictionaryService
        )
    }

    @Provides
    @Singleton
    fun provideWordDao(database: WordDatabase): WordDao {
        return database.wordDao()
    }

    @Provides
    @Singleton
    fun provideDictServiceImpl(): DictionaryServiceImpl {
        return DictionaryServiceImpl(client)
    }

}