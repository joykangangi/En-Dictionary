package com.jkangangi.en_dictionary.app.data.repository

import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    fun postSearch(request: RequestDTO): Flow<NetworkResult<Dictionary?>>

    fun getAllHistory(): Flow<List<DictionaryEntity>>

    suspend fun deleteDictionaryItems(sentences: List<String>)

    suspend fun getDictionaryItem(sentence: String): DictionaryEntity?

}