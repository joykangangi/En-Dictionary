package com.jkangangi.en_dictionary.app.data.remote.network

import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    fun getWord(word: String): Flow<NetworkResult<List<Word>>>
}