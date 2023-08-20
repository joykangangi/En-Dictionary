package com.jkangangi.en_dictionary.app.data.remote.dto2


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InflectionalForm(
    @SerialName("comment")
    val comment: String? = "",
    @SerialName("forms")
    val forms: List<String> = listOf(),
    @SerialName("type")
    val type: String = ""
)