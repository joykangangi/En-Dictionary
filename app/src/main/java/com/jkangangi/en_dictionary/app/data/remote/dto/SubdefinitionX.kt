package com.jkangangi.en_dictionary.app.data.remote.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SubdefinitionX(
    @SerialName("definition")
    val definition: String = "",
    @SerialName("examples")
    val examples: List<String> = listOf()
): Parcelable