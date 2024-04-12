package com.jkangangi.en_dictionary.app.data.repository

import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun postSearch(request: RequestDTO): NetworkResult<DictionaryEntity>

    fun getAllHistory(): Flow<List<DictionaryEntity>>

    suspend fun deleteDictionaryItems(sentences: List<String>)

    suspend fun getDictionaryItem(sentence: String): DictionaryEntity?

}