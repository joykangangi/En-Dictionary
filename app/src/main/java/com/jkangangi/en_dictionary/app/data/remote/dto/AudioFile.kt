package com.jkangangi.en_dictionary.app.data.remote.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AudioFile(
    @SerialName("label")
    val label: String = "",
    @SerialName("link")
    val link: String = ""
): Parcelable