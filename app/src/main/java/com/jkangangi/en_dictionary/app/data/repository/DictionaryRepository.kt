package com.jkangangi.en_dictionary.app.data.repository

import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.coroutines.flow.Flow
//ktor is uses coroutines/asynchronous process to connect to network and get data;hence suspend functions
interface DictionaryRepository {
   // suspend fun getWord(word: String): Flow<NetworkResult<List<Word>>>

    suspend fun postSearch(request: RequestDTO): Flow<NetworkResult<Dictionary>>


}