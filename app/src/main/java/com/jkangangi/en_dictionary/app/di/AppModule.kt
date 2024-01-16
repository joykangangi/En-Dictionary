package com.jkangangi.en_dictionary.app.di

import android.app.Application
import androidx.room.Room
import com.jkangangi.en_dictionary.app.data.local.DictionaryDao
import com.jkangangi.en_dictionary.app.data.local.DictionaryDatabase
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepository
import com.jkangangi.en_dictionary.app.data.repository.DictionaryRepositoryImpl
import com.jkangangi.en_dictionary.app.data.service.DictionaryServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesDictionaryDatabase(application: Application): DictionaryDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = DictionaryDatabase::class.java,
            name = DictionaryDatabase::class.simpleName
        ).build()
    }

    @Provides
    @Singleton
    fun providesDictionaryDao(database: DictionaryDatabase): DictionaryDao {
        return database.dictionaryDao()
    }


    @Provides
    @Singleton
    fun providesDictionaryRepository(
        database: DictionaryDatabase,
        service: DictionaryServiceImpl
    ): DictionaryRepository {
        return DictionaryRepositoryImpl(
            dao = database.dictionaryDao(),
            dictionaryService = service
        )
    }
}