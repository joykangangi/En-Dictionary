package com.jkangangi.en_dictionary.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeaningDto(
    @SerialName("antonyms")
    val antonyms: List<String> = listOf(),
    @SerialName("definitions")
    val definitions: List<DefinitionDto> = listOf(),
    @SerialName("partOfSpeech")
    val partOfSpeech: String = "",
    @SerialName("synonyms")
    val synonyms: List<String> = listOf()
)