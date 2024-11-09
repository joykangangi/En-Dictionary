package com.jkangangi.en_dictionary.app.data.local.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_4 = object : Migration(3,4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE dictionaryentity ADD COLUMN dateInserted TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP")
    }
}