package com.jkangangi.en_dictionary.app.data.repository

import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
   // suspend fun getWord(word: String): Flow<NetworkResult<List<Word>>>

    fun postSearch(request: RequestDTO): Flow<NetworkResult<Dictionary>>


}