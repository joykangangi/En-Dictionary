package com.jkangangi.en_dictionary.app.data.remote.mappers

import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.DictionaryDTO

fun DictionaryDTO.toDictionaryEntity(): DictionaryEntity {
    return DictionaryEntity(
        items = items,
        pronunciations = pronunciations,
        sentence = sentence,
        wordFrequencies = wordFrequencies,
        target = target
    )
}

