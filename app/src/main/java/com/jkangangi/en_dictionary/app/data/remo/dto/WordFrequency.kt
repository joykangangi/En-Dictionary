package com.jkangangi.en_dictionary.app.data.remo.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class WordFrequency(
    @SerialName("frequencies")
    val frequencies: List<Frequency> = listOf(),
    @SerialName("word")
    val word: String = ""
): Parcelable