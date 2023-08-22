package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionaryResponse(searchEntity: DictionaryEntity)

    @Query("DELETE FROM dictionaryentity WHERE sentence IN(:sentence)")
    suspend fun deleteDictionaryResponse(sentence: String)

    @Query("SELECT * FROM dictionaryentity WHERE sentence = TRIM(:sentence)")
    suspend fun getDictionaryResponse(sentence: String): DictionaryEntity

}