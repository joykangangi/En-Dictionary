package com.jkangangi.en_dictionary.app.data.remo.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRequestDTO(
    @SerialName("selection")
    val selection: String = "",
    @SerialName("textAfterSelection")
    val textAfterSelection: String = "",
    @SerialName("textBeforeSelection")
    val textBeforeSelection: String = ""
)