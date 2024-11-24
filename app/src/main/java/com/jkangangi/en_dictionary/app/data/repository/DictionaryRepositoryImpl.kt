package com.jkangangi.en_dictionary.app.data.repository

import android.util.Log
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryDao
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import com.jkangangi.en_dictionary.app.data.remote.dto.trimRequest
import com.jkangangi.en_dictionary.app.data.remote.toDictionaryEntity
import com.jkangangi.en_dictionary.app.data.service.DictionaryService
import com.jkangangi.en_dictionary.app.util.NetworkResult
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * This is Data Source to be used in the view models
 * Get Data from Api/service -> Insert into the Database -> Show the UI
 * All data will come from the database; ie Single Source of Truth
 */

class DictionaryRepositoryImpl(
    private val dao: DictionaryDao,
    private val dictionaryService: DictionaryService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DictionaryRepository {

    override suspend fun postSearch(request: RequestDTO): NetworkResult<DictionaryEntity> {
        return withContext(ioDispatcher) {
            val sentence: String
            /**
             * API -> Database
             */
            try {
                Log.i("repo iml","${dictionaryService.postSearchRequest(request.trimRequest())}")
                val newEntity = dictionaryService.postSearchRequest(search = request.trimRequest()).toDictionaryEntity()
                sentence = newEntity.sentence
                if (newEntity.items.isNotEmpty()) {
                     //db has no duplicate, insert latest
                    dao.deleteDictionaryItems(persistentListOf(newEntity.sentence))
                    dao.insertDictionaryItem(newEntity)
                }

            } catch (e: Throwable) {
                Log.i("REPO IMPL","${e.printStackTrace()}")
                return@withContext NetworkResult.Failure(throwable = e)


            } finally {
                Log.i(this@DictionaryRepositoryImpl::class.java.name, "Network Operation Complete")
            }

            /**
             * Database -> UI
             */
            val newWord = dao.getDictionaryItem(sentence = sentence)
            if (newWord != null && newWord.items.isNotEmpty()) {
                Log.i(this@DictionaryRepositoryImpl::class.java.name, "sentence = $sentence")
                NetworkResult.Success(data = newWord)
            } else {
                NetworkResult.EmptyBody
            }
        }
    }

    override fun getAllHistory(): Flow<List<DictionaryEntity>> {
        return dao.getAllDefinitions()
    }

    override suspend fun getPagingHistory(query: String?, pageSize: Int, offset: Int): List<DictionaryEntity> {
        //delay(500)
        return dao.getPagingHistory(query,pageSize,offset)
    }

    override suspend fun deleteDictionaryItems(sentences: List<String>) {
            dao.deleteDictionaryItems(sentences)
    }

    override suspend fun getDictionaryItem(sentence: String): DictionaryEntity? {
        return dao.getDictionaryItem(sentence)
    }

}