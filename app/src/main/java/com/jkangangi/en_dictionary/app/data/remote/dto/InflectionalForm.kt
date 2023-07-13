package com.jkangangi.en_dictionary.app.data.remote.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class InflectionalForm(
    @SerialName("forms")
    val forms: List<String> = listOf(),
    @SerialName("type")
    val type: String = ""
): Parcelable