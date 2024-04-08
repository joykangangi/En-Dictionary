package com.jkangangi.en_dictionary.app.data.repository

import android.util.Log
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryDao
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.data.remote.dto.trimRequest
import com.jkangangi.en_dictionary.app.data.remote.toDictionaryEntity
import com.jkangangi.en_dictionary.app.data.service.DictionaryService
import com.jkangangi.en_dictionary.app.util.NetworkResult
import com.jkangangi.en_dictionary.app.util.NetworkResult.Error
import io.ktor.client.plugins.RedirectResponseException
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException
import java.net.UnknownServiceException

/**
 * This is Data Source to be used in the view models
 * Get Data from Api/service -> Insert into the Database -> Show the UI
 * All data will come from the database; ie Single Source of Truth
 * TODO - transfer to ViewModel
 */

class DictionaryRepositoryImpl (
    private val dao: DictionaryDao,
    private val dictionaryService: DictionaryService
) : DictionaryRepository {

    override fun postSearch(request: RequestDTO): Flow<NetworkResult<DictionaryEntity>> = flow {
        emit(NetworkResult.Loading())

        var sentence = ""
        /**
         * API -> Database
         */
        try {
            val remoteData = dictionaryService.postSearchRequest(search = request.trimRequest())
            sentence = remoteData.sentence
            if (remoteData.items.isNotEmpty()) {
                val entity = remoteData.toDictionaryEntity() //db has no duplicate, insert latest
                dao.deleteDictionaryItems(persistentListOf(entity.sentence))
                dao.insertDictionaryItem(entity)
            }

        } catch (e: Throwable) {
            when (e) {
                is RedirectResponseException -> {
                    emit(
                        Error(
                            message = "Redirecting to a different page. Please wait."
                        )
                    )

                    Log.e("RepoImpl", "Error 3xx ${e.response.status.description}")
                }

                is UnknownHostException -> {
                    emit(
                        Error(
                            message = "Unable to connect to the server. Check your internet connection"
                        )
                    )

                    Log.e("RepoImpl", "Error 4xx ${e.stackTraceToString()}")

                }

                is UnknownServiceException -> {
                    emit(
                        Error(
                            message = "An unexpected server error. Please try again later."
                        )
                    )
                    Log.e("RepoImpl", "Error 5xx ${e.stackTraceToString()}")
                }

                else -> {
                    emit(
                        Error(
                            message = "An error occurred. Please try again later."
                        )
                    )
                    Log.e("RepoImpl", "Throwable ${e.stackTraceToString()}")

                }
            }
        }
        /**
         * Database -> UI
         */
        val newWord = dao.getDictionaryItem(sentence = sentence)
        emit(NetworkResult.Success(data = newWord))
    }.flowOn(Dispatchers.IO)

    override fun getAllHistory(): Flow<List<DictionaryEntity>> {
        return dao.getAllDefinitions()
    }

    override suspend fun deleteDictionaryItems(sentences: List<String>) {
        dao.deleteDictionaryItems(sentences)
    }

    override suspend fun getDictionaryItem(sentence: String): DictionaryEntity? {
        return dao.getDictionaryItem(sentence)
    }
}


//private fun networkErrors(e: Throwable): Flow<Error<DictionaryEntity>> {
//    val errorResult = when (e) {
//        is RedirectResponseException -> {
//            emit(
//                Error(
//                    data = null,
//                    message = "Redirecting to a different page. Please wait."
//                )
//            )
//
//            Log.e("RepoImpl", "Error 3xx ${e.response.status.description}")
//        }
//
//        is UnknownHostException -> {
//            Error(
//                data = null,
//                message = "Unable to connect to the server. Check your internet connection"
//            )
//
//            Log.e("RepoImpl", "Error 4xx ${e.stackTraceToString()}")
//
//        }
//
//        is UnknownServiceException -> {
//
//            Error(
//                data = null,
//                message = "An unexpected server error. Please try again later."
//            )
//            Log.e("RepoImpl", "Error 5xx ${e.stackTraceToString()}")
//        }
//
//        else -> {
//            Error(
//                data = null,
//                message = "An error occurred. Please try again later."
//            )
//            Log.e("RepoImpl", "Throwable ${e.stackTraceToString()}")
//
//        }
//    }
//    return errorResult
//}