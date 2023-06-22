package com.jkangangi.en_dictionary.app.data.remote.mappers

import com.jkangangi.en_dictionary.app.data.model.Word
import com.jkangangi.en_dictionary.app.data.remote.dto.WordDto

fun WordDto.toWord(): Word {
    return Word(
        license = license.toLicense(),
        meanings = meanings.map { it.toMeaning() },
        phonetic = phonetic,
        phonetics = phonetics.map { it.toPhonetic() },
        sourceUrls = sourceUrls,
        word = word
    )
}