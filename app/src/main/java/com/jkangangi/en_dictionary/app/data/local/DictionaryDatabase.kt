package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


/*
@Database(entities = [SearchEntity::class], version = 2, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class WordDatabase: RoomDatabase() {

        abstract fun wordDao(): WordDao

}*/

@Database(entities = [DictionaryEntity::class], version = 3, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class DictionaryDatabase: RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}