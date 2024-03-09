package com.jkangangi.en_dictionary.app.data.service

import android.util.Log
import com.jkangangi.en_dictionary.BuildConfig
import com.jkangangi.en_dictionary.app.data.remote.dto.DictionaryDTO
import com.jkangangi.en_dictionary.app.data.remote.dto.RequestDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject


const val TIME_OUT = 25_000L

//impl of api
class DictionaryServiceImpl @Inject constructor() : DictionaryService {

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
        private const val BASE_URL = "xf-english-dictionary1.p.rapidapi.com"
        private const val WORD_URL = "https://xf-english-dictionary1.p.rapidapi.com/v1/dictionary"
        private var closableClient: HttpClient? = null

        private fun client(): HttpClient {

            if (closableClient == null) {
                Log.d("Dictionary Service", "Creating httpClient...")

                closableClient = HttpClient(Android) {
                    expectSuccess = true
                    //json serializer/deserializer
                    install(ContentNegotiation) {
                        json(Json {
                            encodeDefaults = true
                            prettyPrint = true
                            ignoreUnknownKeys = true
                            isLenient = true
                        })
                    }

                    install(DefaultRequest) {
                        accept(ContentType.Application.Json)
                        headers {
                            append(name = "content-type", value = ContentType.Application.Json)
                            append(name = "X-RapidAPI-Key", value = API_KEY)
                            append(name = "X-RapidAPI-Host", value = BASE_URL)
                        }
                    }

                    //TIMEOUT
                    install(HttpTimeout) {
                        requestTimeoutMillis = TIME_OUT
                        connectTimeoutMillis = TIME_OUT
                        socketTimeoutMillis = TIME_OUT
                    }


                    //logging requests + responses
                    install(Logging) {
                        logger = object : Logger {
                            override fun log(message: String) {
                                Log.v("Dictionary Service", message)
                            }
                        }
                        level = LogLevel.BODY
                    }

                }
            }
            return closableClient as HttpClient
        }
    }

    override suspend fun postSearchRequest(search: RequestDTO): DictionaryDTO {
        return client().post {
            url(WORD_URL)
            parameter(key = "selection", value = search.selection)
            parameter(key = "textAfterSelection", value = search.textAfterSelection)
            parameter(key = "textBeforeSelection", value = search.textBeforeSelection)

            setBody(search)
        }.body()
    }

    override fun closeClient() {
        Log.d("Dictionary Service", "Closing the client...")
        closableClient?.close()
        closableClient = null
    }


}