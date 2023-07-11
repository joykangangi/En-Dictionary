package com.jkangangi.en_dictionary.app.data.remote.network

import android.util.Log
import com.jkangangi.en_dictionary.app.data.local.WordDao
import com.jkangangi.en_dictionary.app.data.local.toWord
import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.data.remote.dto.DictionaryServiceImpl
import com.jkangangi.en_dictionary.app.data.remote.mappers.toWordEntity
import com.jkangangi.en_dictionary.app.util.NetworkResult
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Data Source to be used in the view models
 * Get Data from Api -> Insert into the Database -> Show the UI
 * All data will come from the database; ie Single Source of Truth
 *
 *
 */

class DictionaryRepositoryImpl @Inject constructor(
    private val dao: WordDao,
    private val dictionaryService: DictionaryServiceImpl
) : DictionaryRepository {

    override suspend fun getWord(word: String): Flow<NetworkResult<List<Word>>> = flow {
        emit(NetworkResult.Loading())

        /**
         * API -> Database
         */
        val localWordData = dao.getWord(word = word).map { it.toWord() }
        emit(NetworkResult.Loading(data = localWordData))

        try {
            val remoteWordData = dictionaryService.getSearchDTO(word = word)
            dao.deleteWord(remoteWordData.map { it.word })
            dao.insertWord(remoteWordData.map { it.toWordEntity() })

        } catch (e: RedirectResponseException) {
            //3xx
            emit(
                NetworkResult.Error(
                    message = "Error ${e.response.status.description}",
                    data = localWordData
                )
            )
            Log.e("RepoImpl",e.response.status.description)
        } catch (e: ClientRequestException) {
            //4xx
            emit(
                NetworkResult.Error(
                    message = "Check your internet connection",
                    data = localWordData
                )
            )
            Log.e("RepoImpl",e.response.status.description)

        } catch (e: ServerResponseException) {
            //5xx
            emit(NetworkResult.Error(message = e.response.status.description, data = localWordData))
            Log.e("RepoImpl",e.response.status.description)
        }

        catch (e: Throwable) {
            emit(NetworkResult.Error(message = e.stackTraceToString(), data = localWordData))
            Log.e("RepoImpl",e.stackTraceToString())
        }

        /**
         * Database -> UI
         */
        val newWord = dao.getWord(word).map { it.toWord() }
        emit(NetworkResult.Success(data = newWord))
    }

    fun closeClient() {
        dictionaryService.closeClient()
    }
}