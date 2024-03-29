package com.jkangangi.en_dictionary.app.util


sealed class NetworkResult<Data>(val data: Data? = null, val message: String? = null) {
    class Loading<Data>(data: Data? = null): NetworkResult<Data>(data = data) //if its a word already in db, show that version in the mean time
    class Success<Data>(data: Data? ): NetworkResult<Data>(data = data)
    class Error<Data>(data: Data? = null, message: String): NetworkResult<Data>(message = message, data = data)

}
