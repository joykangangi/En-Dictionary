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
    suspend fun insertDictionaryItem(dictionaryEntity: DictionaryEntity)

    @Query("DELETE FROM dictionaryentity WHERE sentence IN(:sentences)")
    suspend fun deleteDictionaryItems(sentences: List<String>)

    @Query("SELECT * FROM dictionaryentity WHERE sentence = TRIM(:sentence)")
    suspend fun getDictionaryItem(sentence: String): DictionaryEntity?

    //all History
    @Query("SELECT * FROM dictionaryentity")
    fun getAllDefinitions(): Flow<List<DictionaryEntity>>

}