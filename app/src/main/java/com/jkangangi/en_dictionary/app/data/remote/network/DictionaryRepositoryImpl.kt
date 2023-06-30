package com.jkangangi.en_dictionary.app.data.remote.network

import com.jkangangi.en_dictionary.app.data.local.WordDao
import com.jkangangi.en_dictionary.app.data.local.toWord
import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.data.remote.dto.WordDto
import com.jkangangi.en_dictionary.app.data.remote.mappers.toWord
import com.jkangangi.en_dictionary.app.data.remote.network.ApiRoutes.WORD
import com.jkangangi.en_dictionary.app.util.NetworkResult
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * implement the actual network call using the Ktor client.
 *
 */

class DictionaryRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val dao: WordDao
) : DictionaryRepository {

    override suspend fun getWord(word: String): Flow<NetworkResult<List<Word>>> = flow {
        emit(NetworkResult.Loading())

        val wordData = dao.getWord(word = word).map { it.toWord() }
        emit(NetworkResult.Loading(data = wordData))

         try {
             val remoteWordData = client.get(WORD).body<List<WordDto>>()
             dao.deleteWord(word = remoteWordData.map { it.word })
             dao.insertWord(wordEntities = remoteWordData.map { it.toWord()})
            NetworkResult.Success(client.get(WORD).body())
        } catch (e: RedirectResponseException) {
            //3xx
            Napier.e("Error: ${e.response.status.description}")
            NetworkResult.Error(message = e.stackTraceToString())
        } catch (e: ClientRequestException) {
            //4xx
            Napier.e("Error: ${e.response.status.description}")
            NetworkResult.Error(message = e.stackTraceToString())
        } catch (e: ServerResponseException) {
            //5xx
            Napier.e("Error: ${e.response.status.description}")
            NetworkResult.Error(message = e.stackTraceToString())
        }
    }

}