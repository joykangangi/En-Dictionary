package com.jkangangi.en_dictionary.app.data.remote.dto2


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pronunciation(
    @SerialName("entries")
    val entries: List<Entry> = listOf()
)