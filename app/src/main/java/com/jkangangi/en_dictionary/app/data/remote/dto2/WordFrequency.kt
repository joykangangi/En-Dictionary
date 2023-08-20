package com.jkangangi.en_dictionary.app.data.remote.dto2


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordFrequency(
    @SerialName("frequencies")
    val frequencies: List<Frequency> = listOf(),
    @SerialName("word")
    val word: String = ""
)