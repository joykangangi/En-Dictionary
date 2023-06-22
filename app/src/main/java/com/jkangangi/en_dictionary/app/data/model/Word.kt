package com.jkangangi.en_dictionary.app.data.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Parcelize
data class Word(
    val license: License = License(),
    val meanings: List<Meaning> = listOf(),
    val phonetic: String = "",
    val phonetics: List<Phonetic> = listOf(),
    val sourceUrls: List<String> = listOf(),
    val word: String = ""
): Parcelable