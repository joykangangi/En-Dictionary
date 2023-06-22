package com.jkangangi.en_dictionary.app.data.remote.mappers

import com.jkangangi.en_dictionary.app.data.model.Definition
import com.jkangangi.en_dictionary.app.data.remote.dto.DefinitionDto

fun DefinitionDto.toDefinition(): Definition {
    return Definition(
        antonyms =antonyms,
        definition = definition,
        example = example,
        synonyms = synonyms
    )
}