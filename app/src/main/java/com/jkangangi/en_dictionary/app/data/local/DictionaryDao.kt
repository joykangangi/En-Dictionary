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
    suspend fun insertDictionaryResponse(dictionaryEntity: DictionaryEntity)

    //Delete 1 item
    @Delete
    suspend fun deleteDictionaryItem(dictionaryEntity: DictionaryEntity)

    //clear the whole db
    @Delete
    suspend fun deleteAllDictItems(searchEntities: List<DictionaryEntity>)

    @Query("SELECT * FROM dictionaryentity WHERE sentence = TRIM(:sentence)")
    suspend fun getDictionaryResponse(sentence: String): DictionaryEntity?

    //all History
    @Query("SELECT * FROM dictionaryentity")
    fun getAllDefinitions(): Flow<List<DictionaryEntity>>

}