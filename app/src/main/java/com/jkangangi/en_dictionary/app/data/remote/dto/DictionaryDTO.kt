package com.jkangangi.en_dictionary.app.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//TODO; Change type to nullable from def value
@Serializable
data class DictionaryDTO(
    @SerialName("items")
    val items: List<Item> = listOf(),
    @SerialName("pronunciations")
    val pronunciations: List<Pronunciation> = listOf(),
    @SerialName("sentence")
    val sentence: String = "",
    @SerialName("target")
    val target: String = "",
    @SerialName("wordFrequencies")
    val wordFrequencies: List<WordFrequency> = listOf(),

)