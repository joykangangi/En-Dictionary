package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class WordDatabase: RoomDatabase() {

        abstract fun wordDao(): WordDao

}