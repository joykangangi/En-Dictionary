package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.data.remote.dto.AudioFile
import com.jkangangi.en_dictionary.app.data.remote.dto.Definition
import com.jkangangi.en_dictionary.app.data.remote.dto.Entry
import com.jkangangi.en_dictionary.app.data.remote.dto.Item
import com.jkangangi.en_dictionary.app.data.remote.dto.Pronunciation
import com.jkangangi.en_dictionary.app.data.remote.dto.Textual
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme


@Composable
fun DefinitionScreen(
    dictionary: DictionaryEntity,
    onSpeakerClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        topBar = { DefinitionTopBar(onBack = onBack) },
        content = {
            Column(
                modifier = modifier
                    .padding(it)
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                content = {
                    PhoneticsSection(
                        dictionary = dictionary,
                        onSpeakerClick = onSpeakerClick,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    DefinitionSection(
                        dictionary = dictionary
                    )
                },
            )
        },
    )
}


@Preview(apiLevel = 33)
@Composable
fun WordScreenPreview() {
    En_DictionaryTheme(
        darkTheme = isSystemInDarkTheme(),
        fontFamily = FontFamily.SansSerif,
    ) {
        Scaffold {
            val word = DictionaryEntity(
                items = listOf(
                    Item(
                        partOfSpeech = "noun",
                        definitions = listOf(
                            Definition(
                                definition = "A group of individuals formed to transact business",
                                examples = listOf("A gambling syndicate")
                            )
                        ),
                        synonyms = listOf("Group"," Union")
                    )
                ),
                pronunciations = listOf(
                    Pronunciation(
                        entries = listOf(
                            Entry(
                                audioFiles = listOf(
                                    AudioFile(link = "kkkkk")
                                ),
                                textual = listOf(Textual(pronunciation = "/sIndIket/"))
                            )
                        )
                    )
                ),
                sentence = "Syndicate",
                target = "Syndicate",
                wordFrequencies = emptyList()
            )
            DefinitionScreen(
                modifier = Modifier.padding(it),
                onBack = { },
                onSpeakerClick = { },
                dictionary = word,
            )
        }

    }
}