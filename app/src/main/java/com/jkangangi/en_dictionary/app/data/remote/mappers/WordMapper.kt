package com.jkangangi.en_dictionary.app.data.remote.mappers

import com.jkangangi.en_dictionary.app.data.local.WordEntity
import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.data.remote.dto.WordDto


fun WordDto.toWordEntity(): WordEntity {
    return WordEntity(
        meanings = meanings.map { it.toMeaning() },
        phonetic = phonetic,
        word = word,
        phonetics = phonetics.map { it.toPhonetic() },
        sourceUrls = sourceUrls
    )
}