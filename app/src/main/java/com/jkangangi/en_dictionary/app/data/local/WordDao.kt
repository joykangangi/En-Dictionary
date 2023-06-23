package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(infos: List<WordEntity>)

    @Query("DELETE FROM wordentity WHERE word IN(:words)")
    suspend fun deleteWord(words: List<String>)

    @Query("SELECT * FROM wordentity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWord(word: String): List<WordEntity>
}