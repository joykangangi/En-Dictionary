package com.jkangangi.en_dictionary.app.util

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T?) : NetworkResult<T>
    data class Failure<T>(val throwable: Throwable) : NetworkResult<T>

    data object EmptyBody: NetworkResult<Nothing>

}
