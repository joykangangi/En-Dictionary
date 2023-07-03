package com.jkangangi.en_dictionary.app.di

import android.app.Application
import androidx.room.Room
import com.jkangangi.en_dictionary.app.data.local.WordDatabase
import com.jkangangi.en_dictionary.app.data.remote.dto.DictionaryServiceImpl
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepository
import com.jkangangi.en_dictionary.app.data.remote.network.DictionaryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

const val TIME_OUT = 10_000L

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): WordDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = WordDatabase::class.java,
            name = WordDatabase::class.simpleName
        ).build()
    }

    @Provides
    @Singleton
    fun providesDictionaryRepository(
        wordDatabase: WordDatabase,
        dictionaryService: DictionaryServiceImpl
    ): DictionaryRepository {
        return DictionaryRepositoryImpl(
            dao = wordDatabase.wordDao(),
            dictionaryService = dictionaryService
        )
    }

    @Provides
    @Singleton
    fun provideDictServiceImpl(client: HttpClient): DictionaryServiceImpl {
        return DictionaryServiceImpl(client)
    }


    // @client - asynchronous client to perform HTTP requests(network calls) that use the Ktor HttpClientEngine.
    @Provides
    @Singleton
    fun providesHttpClient() = HttpClient(Android) {
        //json serializer/deserializer
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        //TIMEOUT
        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }


        //default values for each HTTP request,json
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        //logging requests + responses
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(message)
                }
            }
            level = LogLevel.BODY
        }

        //response status
        install(ResponseObserver) {
            onResponse { response ->
                Napier.i("HTTP STATUS: ${response.status.value}")
            }
        }
    }

}