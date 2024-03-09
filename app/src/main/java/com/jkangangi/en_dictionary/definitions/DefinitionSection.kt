package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.Item
import com.jkangangi.en_dictionary.app.data.remote.dto.Phrase

/** Differences:
 *
 * WORD_DFN       | PHRASE_DFN
 *-------------------------------------
 * one word      | greater > one word
 * NO Phrase     | has Phrase class
 * has synonyms  | has no synonyms
 * has antonyms  | has no antonyms
 * has phonetics | has no phonetics
 * can have sound| can have sound
 */
@Composable
fun DefinitionSection(
    dictionary: DictionaryEntity,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        content = {
            dictionary.items.forEach { item ->
                WordDefinition(item = item)
                Thesaurus(item = item)
                item.phrases?.let { PhraseDefinition(phrases = it) }
            }
        },
    )
}

@Composable
private fun WordDefinition(
    modifier: Modifier = Modifier,
    item: Item?,
) {
    Column(
        modifier = modifier,
        content = {
            if (item != null) {
                DefinitionHeader(headerTxt = item.partOfSpeech)
                DefinitionBody(wordDefinitions = item.definitions)
            }
        }
    )
}

@Composable
private fun PhraseDefinition(
    modifier: Modifier = Modifier,
    phrases: List<Phrase?>,
) {
    Column(
        modifier = modifier,
        content = {
            phrases.forEach { phrase ->
                DefinitionHeader(headerTxt = phrase?.phrase)
                DefinitionBody(wordDefinitions = phrase?.definitions)
            }
        }
    )
}

//synonyms + antonyms
@Composable
private fun Thesaurus(item: Item, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(4.dp))
    if (!item.synonyms.isNullOrEmpty()) {
        DefinitionDetail(
            titleText = "Synonyms",
            bodyText = item.synonyms,
            modifier = modifier,
            bodyColor = MaterialTheme.colorScheme.primary
        )
    }
    Spacer(modifier = modifier.height(4.dp))
    if (!item.antonyms.isNullOrEmpty()) {
        DefinitionDetail(
            titleText = "Antonyms",
            bodyText = item.antonyms,
            modifier = modifier,
            bodyColor = MaterialTheme.colorScheme.primary
        )
    }
}