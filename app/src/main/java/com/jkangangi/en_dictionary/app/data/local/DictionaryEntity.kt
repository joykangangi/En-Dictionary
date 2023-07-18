package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.Item
import com.jkangangi.en_dictionary.app.data.remote.dto.Pronunciation
import com.jkangangi.en_dictionary.app.data.remote.dto.WordFrequency
import kotlinx.serialization.Serializable


/*@Serializable
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
}*/

@Serializable
@Entity
data class DictionaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val items: List<Item> = emptyList(),
    val pronunciations: List<Pronunciation> = emptyList(),
    val sentence: String = "",
    val target: String = "",
    val wordFrequencies: List<WordFrequency> = emptyList()
)

    fun DictionaryEntity?.toDictionary(): Dictionary {
        return Dictionary(
            items = this?.items ?: emptyList(),
            pronunciations = this?.pronunciations ?: emptyList(),
            sentence = this?.sentence ?: "",
            target = this?.target ?: "",
            wordFrequencies = this?.wordFrequencies ?: emptyList()
        )
    }

