package com.jkangangi.en_dictionary.word

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.model.Dictionary
import com.jkangangi.en_dictionary.word.tabs.DefinitionWord
import com.jkangangi.en_dictionary.word.tabs.WordList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordScreen(
    modifier: Modifier = Modifier,
    word: Dictionary,
    onSave: () -> Unit,
    onSpeakerClick: () -> Unit,
    onBack: () -> Unit,
) {

    val isWord by rememberSaveable {
        mutableStateOf(word.sentence.contains(" "))
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = word.target,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily.SansSerif
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                content = {

                    Box(
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(12.dp)
                            .weight(.1f),
                        contentAlignment = Alignment.Center,
                        content = {
                            if (isWord) {
                                OneWord(
                                    modifier = modifier,
                                    word = word,
                                    onSpeakerClick = onSpeakerClick,
                                    onSave = onSave
                                )
                            } else {
                                Phrase(
                                    modifier = modifier,
                                    word = word,
                                    onSpeakerClick = onSpeakerClick,
                                    onSave = onSave
                                )
                            }
                        }

                    )
                    TabLayout(modifier = modifier.weight(.2f), word = word, isWord = isWord)
                },
            )
        },
    )
}


@Composable
private fun TabLayout(modifier: Modifier, word: Dictionary, isWord: Boolean) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Definition", "Synonyms", "Antonyms")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(text = title) },
                )
            }
        }
        when (tabIndex) {
            0 -> DefinitionWord(word = word, isWord = isWord)
            1 -> WordList(modifier = modifier, words = listOf("Car", "Bicycle"))
            2 -> WordList(modifier = modifier, words = listOf("Model", "Illustration"))
        }
    }
}

@Preview
@Composable
fun WordScreenPreview() {
    /*En_DictionaryTheme {
        Scaffold {
            val word = Word(
                meanings = listOf(),
                phonetic = "igzaempl",
                word = "example",
                phonetics = listOf(),
                sourceUrls = listOf()
            )
            WordScreen(
                onSave = { },
                onBack = { },
                onSpeakerClick = { },
                word = word
            )
        }

    }*/
}