package com.jkangangi.en_dictionary.app.util

sealed class NetworkResult<T> {
    object Loading: NetworkResult<Nothing>()
    class Success<T>(val data: T): NetworkResult<T>()
    class Error<T>(val message: String): NetworkResult<T>()

}
