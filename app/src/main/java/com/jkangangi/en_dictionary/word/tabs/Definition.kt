package com.jkangangi.en_dictionary.word.tabs

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
fun DefinitionWord(modifier: Modifier = Modifier, word: Dictionary, isWord: Boolean) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (isWord) {
            WordType(
                modifier = modifier,
                wordItems = word.items,
            )
        } else {
            PhraseType(wordItems = word.items)
        }
    }
}


@Composable
private fun PhraseType(
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
private fun WordType(
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
                    Text(
                        text = item.partOfSpeech,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily.SansSerif,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Divider()
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