package com.jkangangi.en_dictionary.app.data.remote.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Subdefinition(
    @SerialName("definition")
    val definition: String = "",
    @SerialName("examples")
    val examples: List<String>? = listOf(),
    @SerialName("subdefinitions")
    val subdefinitions: List<SubdefinitionX>? = listOf(),
    @SerialName("synonyms")
    val synonyms: List<String>? = listOf()
): Parcelable