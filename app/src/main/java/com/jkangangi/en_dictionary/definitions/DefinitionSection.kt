package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.Item
import com.jkangangi.en_dictionary.app.data.remote.dto.Phrase
import com.jkangangi.en_dictionary.app.util.isWord

/**
 * WordDFN       | PhraseDFN
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
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(12.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
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