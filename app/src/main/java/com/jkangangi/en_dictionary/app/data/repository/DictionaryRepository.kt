package com.jkangangi.en_dictionary.app.data.repository

import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun postSearch(request: RequestDTO): Flow<NetworkResult<Dictionary?>>

    suspend fun getDictionary(sentence: String): Flow<Dictionary?>

}