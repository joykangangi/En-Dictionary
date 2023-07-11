package com.jkangangi.en_dictionary.app.data.network

import com.jkangangi.en_dictionary.app.data.remo.dto.SearchRequestDTO
import com.jkangangi.en_dictionary.app.data.remo.dto.SearchResponseDTO


//what to do with the Api nb: Search can be a word or phrase
interface DictionaryService {
   // suspend fun getSearchResponse(): List<PostResponseDTO>

    suspend fun postSearchRequest(postRequestDTO: SearchRequestDTO): SearchResponseDTO?

}

