package com.jkangangi.en_dictionary.app.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jkangangi.en_dictionary.app.data.model.Meaning
import com.jkangangi.en_dictionary.app.data.model.Phonetic
import com.jkangangi.en_dictionary.app.data.model.Word
import kotlinx.parcelize.Parcelize


@Entity
data class WordEntity(
    @PrimaryKey val id: Long? = null,
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
) {
   fun toWord(): Word {
       return Word(
           meanings = meanings,
           phonetic = phonetic,
           word = word,
           license = null,
           sourceUrls = null,
           phonetics = null
       )
   }
}
