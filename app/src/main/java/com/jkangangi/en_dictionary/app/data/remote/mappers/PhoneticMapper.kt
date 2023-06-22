package com.jkangangi.en_dictionary.app.data.remote.mappers

import com.jkangangi.en_dictionary.app.data.model.Phonetic
import com.jkangangi.en_dictionary.app.data.remote.dto.PhoneticDto

fun PhoneticDto.toPhonetic(): Phonetic {
    return Phonetic(
        audio = audio,
        license = license.toLicense(),
        sourceUrl = sourceUrl,
        text = text
    )
}