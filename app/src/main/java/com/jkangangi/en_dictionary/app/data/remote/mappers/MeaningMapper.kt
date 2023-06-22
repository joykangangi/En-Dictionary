package com.jkangangi.en_dictionary.app.data.remote.mappers

import com.jkangangi.en_dictionary.app.data.model.Meaning
import com.jkangangi.en_dictionary.app.data.remote.dto.MeaningDto

fun MeaningDto.toMeaning(): Meaning {
    return Meaning(
        antonyms = antonyms,
        definitions = definitions.map {
            it.toDefinition()
        },
        partOfSpeech = partOfSpeech,
        synonyms = synonyms
    )
}