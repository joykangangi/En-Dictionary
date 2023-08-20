package com.jkangangi.en_dictionary.app.data.remote.dto2


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Frequency(
    @SerialName("frequencyBand")
    val frequencyBand: String = "",
    @SerialName("partOfSpeech")
    val partOfSpeech: String = ""
)