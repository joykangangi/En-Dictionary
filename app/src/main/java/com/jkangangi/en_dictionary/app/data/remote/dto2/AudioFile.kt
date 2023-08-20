package com.jkangangi.en_dictionary.app.data.remote.dto2


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AudioFile(
    @SerialName("label")
    val label: String = "",
    @SerialName("link")
    val link: String = ""
)