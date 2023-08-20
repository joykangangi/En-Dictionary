package com.jkangangi.en_dictionary.app.data.remote.dto2


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Entry(
    @SerialName("audioFiles")
    val audioFiles: List<AudioFile> = listOf(),
    @SerialName("entry")
    val entry: String = "",
    @SerialName("textual")
    val textual: List<Textual>? = listOf()
)