package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DictionaryDao {

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(wordEntities: List<WordEntity>)

    @Query("DELETE FROM wordentity WHERE word IN(:words)")
    suspend fun deleteWord(words: List<String>)

    @Query("SELECT * FROM wordentity WHERE word LIKE '%' || :word || '%'") //wildcard
    suspend fun getWord(word: String): List<WordEntity>

*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionaryResponse(searchEntity: DictionaryEntity)

    @Query("DELETE FROM dictionaryentity WHERE target IN(:target)")
    suspend fun deleteDictionaryResponse(target: String)

    @Query("SELECT * FROM dictionaryentity WHERE sentence LIKE '%' || :sentence || '%'") //wildcard
    suspend fun getDictionaryResponse(sentence: String): DictionaryEntity


}