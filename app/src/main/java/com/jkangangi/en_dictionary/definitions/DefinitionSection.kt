package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.Item
import com.jkangangi.en_dictionary.app.data.remote.dto.Phrase
import com.jkangangi.en_dictionary.app.util.isWord

/** Differences:
 *
 * WORD_DFN       | PHRASE_DFN
 *-------------------------------------
 * one word      | greater > one word
 * NO Phrase     | has Phrase class
 * has synonyms  | has no synonyms
 * has antonyms  | has no antonyms
 * has phonetics | has no phonetics
 *
 */
@Composable
fun DefinitionSection(
    dictionary: DictionaryEntity,
    modifier: Modifier,
) {
    val isWord=  dictionary.sentence.isWord()

    Column(
        modifier = modifier
            .padding(6.dp)
            .fillMaxWidth(),
        content = {
            dictionary.items.forEach { item ->
                if (isWord){
                    WordDefinition(item = item)
                    Thesaurus(item = item)
                }
                else{
                    item.phrases?.let { phrases ->  
                        PhraseDefinition(phrases = phrases)
                    }
                }
            }
        },
    )
}

@Composable
private fun WordDefinition(
    modifier: Modifier = Modifier,
    item: Item,
) {
    Column {
        DefinitionHeader(headerTxt = item.partOfSpeech, modifier = modifier)
        DefinitionBody(wordDefinitions = item.definitions, modifier = modifier)
    }
}

@Composable
private fun PhraseDefinition(
    modifier: Modifier = Modifier,
    phrases: List<Phrase?>,
) {
    Column {
        phrases.forEach { phrase ->
            DefinitionHeader(modifier = modifier, headerTxt = phrase?.phrase)
            DefinitionBody(wordDefinitions = phrase?.definitions, modifier = modifier)
        }
    }
}

//synonyms + antonyms
@Composable
private fun Thesaurus(item: Item, modifier: Modifier = Modifier) {
    if (!item.synonyms.isNullOrEmpty()) {
        DefinitionDetail(titleText = "Synonyms", bodyText = item.synonyms, modifier = modifier)
    }
    if (!item.antonyms.isNullOrEmpty()) {
        DefinitionDetail(titleText = "Antonyms", bodyText = item.antonyms, modifier = modifier)
    }
}