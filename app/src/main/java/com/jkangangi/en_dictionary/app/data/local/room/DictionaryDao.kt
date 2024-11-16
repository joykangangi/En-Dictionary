package com.jkangangi.en_dictionary.app.data.local.room

import androidx.room.Dao
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
    @Query("SELECT * FROM dictionaryentity ORDER BY dateInserted DESC")
    fun getAllDefinitions(): Flow<List<DictionaryEntity>>

    /**
     * getPagingHistory(limit = 10, offset = 0)-first 10
     * getPagingHistory(limit = 10, offset = 10)- second 10
     * getPagingHistory(limit = 10, offset = 20)-3rd 10
     * ||-string concatenation
     */
    @Query("""
        SELECT * FROM dictionaryentity 
        WHERE (:searchQuery IS NULL OR :searchQuery = '' OR sentence LIKE '%' || :searchQuery || '%' COLLATE NOCASE)
        ORDER BY dateInserted DESC
        LIMIT :limit 
        OFFSET :offset
    """)
    suspend fun getPagingHistory(
        searchQuery: String?,
        limit: Int,
        offset: Int
    ): List<DictionaryEntity>
}