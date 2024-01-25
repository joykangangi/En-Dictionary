package com.jkangangi.en_dictionary.app.data.local.room

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.Item
import com.jkangangi.en_dictionary.app.data.remote.dto.Pronunciation
import com.jkangangi.en_dictionary.app.data.remote.dto.WordFrequency
import kotlinx.serialization.Serializable

@Stable
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

//the first run loading from local, the entity is empty;hence handle null
fun DictionaryEntity?.toDictionary(): Dictionary {
    return Dictionary(
        items = this?.items ?: emptyList(),
        pronunciations = this?.pronunciations ?: emptyList(),
        sentence = this?.sentence ?: "",
        target = this?.target ?: "",
        wordFrequencies = this?.wordFrequencies ?: emptyList()
    )
}


