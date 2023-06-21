package com.jkangangi.en_dictionary.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DefinitionDto(
    @SerialName("antonyms")
    val antonyms: List<String> = listOf(),
    @SerialName("definition")
    val definition: String = "",
    @SerialName("example")
    val example: String = "",
    @SerialName("synonyms")
    val synonyms: List<String> = listOf()
)