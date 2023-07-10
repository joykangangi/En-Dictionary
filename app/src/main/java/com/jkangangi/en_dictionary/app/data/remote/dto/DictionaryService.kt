package com.jkangangi.en_dictionary.app.data.remote.dto

import android.util.Log
import com.jkangangi.en_dictionary.app.data.remote.network.ApiRoutes.BASE_URL
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


//what to do with the Api
interface DictionaryService {
    suspend fun getWordDTO(word: String): List<WordDto>

}

//impl of api


class DictionaryServiceImpl(private val client: HttpClient) : DictionaryService {

    private var closableClient: HttpClient? = null

    private fun client(): HttpClient {
        if (closableClient == null) {
            Log.d("Dictionary Service","Creating httpClient...")
            closableClient = client
        }
        return closableClient as HttpClient
    }

    override suspend fun getWordDTO(word: String): List<WordDto> {
        return client().get("$BASE_URL$word").body()
    }

    fun closeClient() {
        Log.d("Dictionary Service","Closing the client...")
        closableClient?.close()
        closableClient = null
    }


}

/*class DictionaryServiceImpl1 @Inject constructor() : DictionaryService {

    companion object {
        private var client: HttpClient? = null

        fun getClient(): HttpClient {
            if (client == null) {
                Napier.d("Creating httpClient...")
                client =
            }
        }
    }

    override suspend fun getWordDTO(word: String): List<WordDto> {
        TODO("Not yet implemented")
    }

}*/
