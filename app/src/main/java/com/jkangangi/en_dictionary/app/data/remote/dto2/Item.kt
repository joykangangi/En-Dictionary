package com.jkangangi.en_dictionary.app.data.remote.dto2


import com.jkangangi.en_dictionary.app.data.remote.dto.Phrase
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("antonyms")
    val antonyms: List<String>? = listOf(),
    @SerialName("definitions")
    val definitions: List<Definition>? = listOf(),
    @SerialName("inflectionalForms")
    val inflectionalForms: List<InflectionalForm>? = listOf(),
    @SerialName("partOfSpeech")
    val partOfSpeech: String? = "",
    @SerialName("phrases")
    val phrases: List<Phrase>? = listOf(),
    @SerialName("synonyms")
    val synonyms: List<String>? = listOf(),
    @SerialName("word")
    val word: String? = ""
)