package com.jkangangi.en_dictionary.definitions.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.app.data.remote.dto.Item

@Composable
fun Definition(modifier: Modifier = Modifier, word: Dictionary, isWord: Boolean) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (isWord) {
            WordDefinition(
                modifier = modifier,
                wordItems = word.items,
            )
        } else {
            PhraseDefinition(wordItems = word.items)
        }
    }
}


@Composable
private fun PhraseDefinition(
    wordItems: List<Item>,
) {
    Column {
        wordItems.forEach { item ->
            item.phrases?.let { phrases ->
                PhraseDfnComponent(phraseDefinitions = phrases)
            }
        }
    }
}

@Composable
private fun WordDefinition(
    modifier: Modifier,
    wordItems: List<Item>,
) {
    Column {
        wordItems.forEach { item ->
            Row(
                modifier = modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    Divider(modifier = modifier.weight(0.2f))
                    Text(
                        modifier = modifier.weight(0.6f),
                        text = item.partOfSpeech,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily.SansSerif,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Divider(modifier = modifier.weight(0.2f))
                }
            )
            WordDfnComponent(wordDefinitions = item.definitions)
        }
    }
}

@Preview
@Composable
private fun DefinitionPreview() {
    /*En_DictionaryTheme {
        Scaffold {
            val meaning = Definition(

            )
            DefinitionScreen(modifier = Modifier, meaning = meaning)
        }
    }*/
}