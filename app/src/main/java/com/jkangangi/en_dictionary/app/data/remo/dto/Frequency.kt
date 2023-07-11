package com.jkangangi.en_dictionary.app.data.remo.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Frequency(
    @SerialName("frequencyBand")
    val frequencyBand: String = "",
    @SerialName("partOfSpeech")
    val partOfSpeech: String = ""
): Parcelable