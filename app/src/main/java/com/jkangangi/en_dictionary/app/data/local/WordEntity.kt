package com.jkangangi.en_dictionary.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jkangangi.en_dictionary.app.data.model.Meaning
import com.jkangangi.en_dictionary.app.data.model.Word
import kotlinx.serialization.Serializable


@Serializable
@Entity
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
)

fun WordEntity.toWord(): Word {
    return Word(
        meanings = meanings,
        phonetic = phonetic,
        word = word
    )
}

