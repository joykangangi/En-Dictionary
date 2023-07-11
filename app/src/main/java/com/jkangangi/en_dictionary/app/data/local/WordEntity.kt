package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jkangangi.en_dictionary.app.data.model.Meaning
import com.jkangangi.en_dictionary.app.data.model.Phonetic
import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.data.remo.dto.Item
import com.jkangangi.en_dictionary.app.data.remo.dto.Pronunciation
import com.jkangangi.en_dictionary.app.data.remo.dto.WordFrequency
import kotlinx.serialization.Serializable


@Serializable
@Entity
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>
)

fun WordEntity.toWord(): Word {
    return Word(
        meanings = meanings,
        phonetic = phonetic,
        word = word,
        phonetics = phonetics,
        sourceUrls = sourceUrls
    )
}

@Serializable
@Entity
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val items: List<Item>,
    val pronunciations: List<Pronunciation>,
    val sentence: String,
    val target: String,
    val wordFrequencies: List<WordFrequency>
)

fun WordEntity.toWord(): Word {
    return Word(
        meanings = meanings,
        phonetic = phonetic,
        word = word,
        phonetics = phonetics,
        sourceUrls = sourceUrls
    )
}

