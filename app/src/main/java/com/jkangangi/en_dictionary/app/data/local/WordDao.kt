package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(wordEntities: List<WordEntity>)

    @Delete
    suspend fun deleteWord(word: List<String>)

    @Query("SELECT * FROM wordentity WHERE word LIKE '%' || :word || '%'") //wildcard
    suspend fun getWord(word: String): List<WordEntity>
}