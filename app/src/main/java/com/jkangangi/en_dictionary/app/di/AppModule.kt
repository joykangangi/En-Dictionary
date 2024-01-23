package com.jkangangi.en_dictionary.app.di

import android.app.Application
import androidx.room.Room
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryDao
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryDatabase
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

}