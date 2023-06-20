package com.jkangangi.en_dictionary.app.data.remote

import com.jkangangi.en_dictionary.app.data.remote.dto.WordDto
import com.jkangangi.en_dictionary.app.util.NetworkResult
import io.ktor.client.call.body
import io.ktor.client.request.get

object DictionaryApi {

    private const val BASE_URL = "https://api.dictionaryapi.dev/"

    suspend fun getWord(): NetworkResult<List<WordDto>> {
      
    }


}