package com.jkangangi.en_dictionary.app.util

sealed class NetworkResult<T> {
    class Loading<T>: NetworkResult<T>()
    class Success<T>(val data: T): NetworkResult<T>()
    class Error<T>(val code: Int, val message: String): NetworkResult<T>()

}
