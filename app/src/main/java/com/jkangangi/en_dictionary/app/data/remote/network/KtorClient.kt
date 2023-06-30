package com.jkangangi.en_dictionary.app.data.remote.network

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
import javax.inject.Inject

const val TIME_OUT = 10_000L


//functions that will help process api data
// @client - asynchronous client to perform HTTP requests(network calls) that use the Ktor HttpClientEngine.
class KtorHttpClient @Inject constructor() {

    fun getHttpClient() = HttpClient(Android) {

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