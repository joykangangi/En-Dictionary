package com.jkangangi.en_dictionary.app.data.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Parcelize
data class Phonetic(
    val audio: String = "",
    val license: License = License(),
    val sourceUrl: String = "",
    val text: String = ""
): Parcelable