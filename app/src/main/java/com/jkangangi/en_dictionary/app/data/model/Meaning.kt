package com.jkangangi.en_dictionary.app.data.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Parcelize
data class Meaning(
    val antonyms: List<String> = listOf(),
    val definitions: List<Definition> = listOf(),
    val partOfSpeech: String = "",
    val synonyms: List<String> = listOf()
): Parcelable