package com.jkangangi.en_dictionary.app.data.remote.dto

import com.jkangangi.en_dictionary.app.data.remote.network.ApiRoutes.WORD_URL
import com.jkangangi.en_dictionary.app.util.NetworkResult
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get


//what to do with the Api
interface DictionaryService {
    suspend fun getWordDTO(word: String): List<WordDto>

}

class DictionaryServiceImpl(private val client: HttpClient) : DictionaryService {
    override suspend fun getWordDTO(word: String): List<WordDto> {
        return try {
            client.get(WORD_URL).body()
        } catch (e: RedirectResponseException) {
            //3xx
            Napier.e("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: RedirectResponseException) {
            //3xx
            Napier.e("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ClientRequestException) {
            //4xx
            Napier.e("Error: ${e.response.status.description}")
            emptyList()

        } catch (e: ServerResponseException) {
            //5xx
            Napier.e("Error: ${e.response.status.description}")
            emptyList()
        }
    }
}