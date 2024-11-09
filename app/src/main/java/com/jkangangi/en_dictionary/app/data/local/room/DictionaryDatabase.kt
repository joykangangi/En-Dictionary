package com.jkangangi.en_dictionary.app.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters



@Database(entities = [DictionaryEntity::class], version = 4, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class DictionaryDatabase: RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}