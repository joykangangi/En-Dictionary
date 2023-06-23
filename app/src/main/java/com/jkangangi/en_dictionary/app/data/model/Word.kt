package com.jkangangi.en_dictionary.app.data.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Parcelize
data class Word(
    val license: License?,
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>?,
    val sourceUrls: List<String>?,
    val word: String
): Parcelable