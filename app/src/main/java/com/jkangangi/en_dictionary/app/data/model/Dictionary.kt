package com.jkangangi.en_dictionary.app.data.model


import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.Item
import com.jkangangi.en_dictionary.app.data.remote.dto.Pronunciation
import com.jkangangi.en_dictionary.app.data.remote.dto.WordFrequency
import kotlinx.parcelize.Parcelize

//TODO; remove def value
@Stable
@Parcelize
data class Dictionary(
    val items: List<Item> = emptyList(),
    val pronunciations: List<Pronunciation> = emptyList(),
    val sentence: String = "",
    val target: String = "",
    val wordFrequencies: List<WordFrequency> = emptyList()
) : Parcelable

fun Dictionary.toDictionaryEntity(): DictionaryEntity {
    return DictionaryEntity(
        items = this.items,
        pronunciations = this.pronunciations,
        sentence = this.sentence,
        target = this.target,
        wordFrequencies = this.wordFrequencies
    )
}