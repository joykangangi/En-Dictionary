package com.jkangangi.en_dictionary.app.data.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class Word(
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>
): Parcelable