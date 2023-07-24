package com.jkangangi.en_dictionary.app.data.remote.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Item(
    @SerialName("antonyms")
    val antonyms: List<String> = listOf(),
    @SerialName("definitions")
    val definitions: List<Definition> = listOf(),
    @SerialName("inflectionalForms")
    val inflectionalForms: List<InflectionalForm> = listOf(),
    @SerialName("partOfSpeech")
    val partOfSpeech: String = "",
    @SerialName("synonyms")
    val synonyms: List<String> = listOf(),
    @SerialName("word")
    val word: String = ""
): Parcelable