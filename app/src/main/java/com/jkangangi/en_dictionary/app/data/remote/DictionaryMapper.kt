package com.jkangangi.en_dictionary.app.data.remote

import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.DictionaryDTO


fun DictionaryDTO.toDictionaryEntity(): DictionaryEntity {
    return DictionaryEntity(
        items = this.items ,
        pronunciations = this.pronunciations,
        sentence = sentence,
        wordFrequencies = wordFrequencies,
        target = target
    )
}

