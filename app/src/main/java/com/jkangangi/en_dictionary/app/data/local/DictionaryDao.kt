package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionaryResponse(searchEntity: DictionaryEntity)

    @Delete
    suspend fun deleteDictionaryResponse(searchEntity: DictionaryEntity)

    @Query("SELECT * FROM dictionaryentity WHERE sentence = TRIM(:sentence)")
    suspend fun getDictionaryResponse(sentence: String): DictionaryEntity?

}