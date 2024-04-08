package com.jkangangi.en_dictionary.app.util


sealed class NetworkResult<Data>(val data: Data? = null, val message: String? = null) {
    class Loading<Data>(data: Data? = null): NetworkResult<Data>(data = data)
    class Success<Data>(data: Data? ): NetworkResult<Data>(data = data)
    class Error<Data>(data: Data? = null, message: String): NetworkResult<Data>(message = message, data = data)

}

//sealed interface NetworkResult<out Data, out Error> {
//    data class Success<out Data>(val data: Data): NetworkResult<Data, Error>
//   data class Error<out Data>(val message: String, val data: Data? = null): NetworkResult<Data, Error>
// Todo -> change results type
//}
