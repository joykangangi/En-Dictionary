package com.jkangangi.en_dictionary.app.data.service

import com.jkangangi.en_dictionary.app.data.remote.dto.DictionaryDTO
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO


//what to do with the Api nb: Search can be a word or phrase
interface DictionaryService {

    suspend fun postSearchRequest(search: RequestDTO): DictionaryDTO

}

