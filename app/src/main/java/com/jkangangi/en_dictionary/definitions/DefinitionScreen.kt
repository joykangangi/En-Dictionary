package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.local.room.DictionaryEntity
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefinitionScreen(
    modifier: Modifier = Modifier,
    dictionary: DictionaryEntity,
    onSpeakerClick: () -> Unit,
    onBack: () -> Unit,
) {

    Scaffold(
        modifier = modifier,
        topBar = { DefinitionTopBar(onBack = onBack) },
        content = {
            Column(
                modifier = Modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    PhoneticsSection(
                        modifier = modifier,
                        dictionary = dictionary,
                        onSpeakerClick = onSpeakerClick
                    )
                    DefinitionSection(
                        dictionary = dictionary ,
                        modifier = modifier
                    )
                },
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WordScreenPreview() {
    En_DictionaryTheme {
        Scaffold {
            val word = DictionaryEntity()
            DefinitionScreen(
                modifier = Modifier.padding(it),
                onBack = { },
                onSpeakerClick = { },
                dictionary = word
            )
        }

    }
}