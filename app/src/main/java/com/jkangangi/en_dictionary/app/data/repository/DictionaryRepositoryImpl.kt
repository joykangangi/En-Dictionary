package com.jkangangi.en_dictionary.app.data.repository

import android.util.Log
import com.jkangangi.en_dictionary.app.data.local.DictionaryDao
import com.jkangangi.en_dictionary.app.data.local.toDictionary
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.data.remote.toDictionaryEntity
import com.jkangangi.en_dictionary.app.data.service.DictionaryServiceImpl
import com.jkangangi.en_dictionary.app.util.NetworkResult
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This is Data Source to be used in the view models
 * Get Data from Api -> Insert into the Database -> Show the UI
 * All data will come from the database; ie Single Source of Truth
 *
 */

class DictionaryRepositoryImpl @Inject constructor(
    private val dao: DictionaryDao,
    private val dictionaryService: DictionaryServiceImpl
) : DictionaryRepository {

    override suspend fun postSearch(request: RequestDTO): Flow<NetworkResult<Dictionary?>> = flow {
        emit(NetworkResult.Loading())

        val sentence = "${request.textBeforeSelection} ${request.selection} ${request.textAfterSelection}"
        val localData = dao.getDictionaryResponse(sentence = sentence).toDictionary()
        emit(NetworkResult.Loading(data = localData))

        /**
         * API -> Database
         */
        try {
            val remoteData = dictionaryService.postSearchRequest(search = request)
            if (remoteData != null) {
                //dao.deleteDictionaryResponse(remoteData.toDictionaryEntity())
                dao.insertDictionaryResponse(remoteData.toDictionaryEntity())
            }

        } catch (e: RedirectResponseException) {
            //3xx
            emit(
                NetworkResult.Error(
                    message = "Redirecting to a different page. Please wait."
                )
            )
            Log.e("RepoImpl", "Error 300 ${e.response.status.description}")
        } catch (e: ClientRequestException) {
            //4xx
            emit(
                NetworkResult.Error(
                    message = "Check your internet connection"
                )
            )
            Log.e("RepoImpl", "Error 400 ${e.response.status.description}")

        } catch (e: ServerResponseException) {
            //5xx
            emit(
                NetworkResult.Error(
                    message = "An unexpected server error. Please try again later."
                )
            )
            Log.e("RepoImpl", "Error 500 ${e.response.status.description}")
        } catch (e: Throwable) {
            emit(
                NetworkResult.Error(
                    message = "An error occurred. Please try again later."
                )
            )
            Log.e("RepoImpl", "Throwable ${e.stackTraceToString()}")
        }

        /**
         * Database -> UI
         */
        val newWord = dao.getDictionaryResponse(sentence = sentence).toDictionary()
        emit(NetworkResult.Success(data = newWord))
    }

    override suspend fun getDictionary(sentence: String): Flow<Dictionary?> = flow {
        val dictionary = dao.getDictionaryResponse(sentence).toDictionary()
        Log.i("DICT REPO IMPL","DICTIONARY = $dictionary")
        emit(dictionary)
    }

    fun closeClient() {
        dictionaryService.closeClient()
    }
}