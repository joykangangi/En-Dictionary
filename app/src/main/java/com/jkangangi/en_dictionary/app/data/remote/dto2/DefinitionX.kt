package com.jkangangi.en_dictionary.app.data.remote.dto2


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DefinitionX(
    @SerialName("definition")
    val definition: String = "",
    @SerialName("examples")
    val examples: List<String>? = listOf()
)