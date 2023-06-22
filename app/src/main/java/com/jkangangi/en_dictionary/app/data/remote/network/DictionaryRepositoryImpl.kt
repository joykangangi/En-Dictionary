package com.jkangangi.en_dictionary.app.data.remote.network

import com.jkangangi.en_dictionary.app.data.remote.dto.WordDto
import com.jkangangi.en_dictionary.app.data.remote.network.ApiRoutes.WORD
import com.jkangangi.en_dictionary.app.util.NetworkResult
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * implement the actual network call using the Ktor client.
 */

class DictionaryRepositoryImpl @Inject constructor (private val client: HttpClient): DictionaryRepository {

    override suspend fun getWord(): NetworkResult<List<WordDto>> {
        return try {
            NetworkResult.Success(client.get(WORD).body())
        } catch (e: Exception) {
            Napier.e(e.stackTraceToString())
            NetworkResult.Error(message = e.stackTraceToString())
        }
    }

}