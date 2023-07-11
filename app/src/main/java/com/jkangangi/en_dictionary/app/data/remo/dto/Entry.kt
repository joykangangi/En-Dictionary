package com.jkangangi.en_dictionary.app.data.remo.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Entry(
    @SerialName("audioFiles")
    val audioFiles: List<AudioFile> = listOf(),
    @SerialName("entry")
    val entry: String = "",
    @SerialName("textual")
    val textual: List<Textual> = listOf()
): Parcelable