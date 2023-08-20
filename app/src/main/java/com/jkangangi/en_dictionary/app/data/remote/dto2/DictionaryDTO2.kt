package com.jkangangi.en_dictionary.app.data.remote.dto2


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DictionaryDTO2(
    @SerialName("items")
    val items: List<Item> = listOf(),
    @SerialName("pronunciations")
    val pronunciations: List<Pronunciation> = listOf(),
    @SerialName("sentence")
    val sentence: String = "",
    @SerialName("target")
    val target: String = "",
    @SerialName("wordFrequencies")
    val wordFrequencies: List<WordFrequency> = listOf()
)