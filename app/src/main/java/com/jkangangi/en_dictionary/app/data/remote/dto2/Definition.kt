package com.jkangangi.en_dictionary.app.data.remote.dto2


import com.jkangangi.en_dictionary.app.data.remote.dto.Subdefinition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Definition(
    @SerialName("definition")
    val definition: String = "",
    @SerialName("examples")
    val examples: List<String>? = listOf(),
    @SerialName("subdefinitions")
    val subdefinitions: List<Subdefinition>? = listOf()
)