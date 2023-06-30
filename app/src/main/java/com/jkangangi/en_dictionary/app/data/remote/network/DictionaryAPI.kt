package com.jkangangi.en_dictionary.app.data.remote.network

import com.jkangangi.en_dictionary.app.data.remote.dto.WordDto
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.coroutines.flow.Flow

//what to do with the Api
interface DictionaryAPI {
    suspend fun getWordDTO(word: String): Flow<NetworkResult<List<WordDto>>>
}