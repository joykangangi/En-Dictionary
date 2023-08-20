package com.jkangangi.en_dictionary.app.data.repository

import android.util.Log
import com.jkangangi.en_dictionary.app.data.local.DictionaryDao
import com.jkangangi.en_dictionary.app.data.local.toDictionary
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.data.service.DictionaryServiceImpl
import com.jkangangi.en_dictionary.app.data.remote.toDictionaryEntity
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
/*override suspend fun getWord(word: String): Flow<NetworkResult<List<Word>>> = flow {
       emit(NetworkResult.Loading())


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
           Log.e("RepoImpl", e.response.status.description)
       } catch (e: ClientRequestException) {
           //4xx
           emit(
               NetworkResult.Error(
                   message = "Check your internet connection",
                   data = localWordData
               )
           )
           Log.e("RepoImpl", e.response.status.description)

       } catch (e: ServerResponseException) {
           //5xx
           emit(NetworkResult.Error(message = e.response.status.description, data = localWordData))
           Log.e("RepoImpl", e.response.status.description)
       } catch (e: Throwable) {
           emit(NetworkResult.Error(message = e.stackTraceToString(), data = localWordData))
           Log.e("RepoImpl", e.stackTraceToString())
       }
       val newWord = dao.getWord(word).map { it.toWord() }
       emit(NetworkResult.Success(data = newWord))
   }*/

class DictionaryRepositoryImpl @Inject constructor(
    private val dao: DictionaryDao,
    private val dictionaryService: DictionaryServiceImpl
) : DictionaryRepository {

    override fun postSearch(request: RequestDTO): Flow<NetworkResult<Dictionary>> = flow {
        emit(NetworkResult.Loading())

        val localData = dao.getDictionaryResponse(sentence = request.selection).toDictionary()
        emit(NetworkResult.Loading(data = localData))

        /**
         * API -> Database
         */
        try {
            val remoteData = dictionaryService.postSearchRequest(search = request)
            Log.d("DICT REPOSITORY IMPL","sele = ${request.selection}, AFR = ${request.textAfterSelection}, bFERE = ${request.textBeforeSelection}")

            if (remoteData != null) {
                dao.deleteDictionaryResponse(remoteData.target)
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
        val newWord = dao.getDictionaryResponse(sentence = request.selection).toDictionary()
        emit(NetworkResult.Success(data = newWord))
    }

    fun closeClient() {
        dictionaryService.closeClient()
    }
}