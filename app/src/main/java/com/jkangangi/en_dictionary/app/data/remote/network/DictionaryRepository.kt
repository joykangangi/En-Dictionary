package com.jkangangi.en_dictionary.app.data.remote.network

import com.jkangangi.en_dictionary.app.data.remote.dto.WordDto
import com.jkangangi.en_dictionary.app.util.NetworkResult

interface DictionaryRepository {
    suspend fun getWord(): NetworkResult<List<WordDto>>
}