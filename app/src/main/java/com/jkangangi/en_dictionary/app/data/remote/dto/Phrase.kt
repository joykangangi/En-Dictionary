package com.jkangangi.en_dictionary.app.data.remote.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Phrase(
    @SerialName("definitions")
    val definitions: List<Definition> = listOf(),
    @SerialName("partOfSpeech")
    val partOfSpeech: String = "",
    @SerialName("phrase")
    val phrase: String = ""
): Parcelable