package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class WordDatabase: RoomDatabase() {

        abstract fun wordDao(): WordDao

}