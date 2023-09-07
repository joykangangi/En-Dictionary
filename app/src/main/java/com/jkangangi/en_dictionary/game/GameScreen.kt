package com.jkangangi.en_dictionary.game

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.widgets.EmptyListView
import kotlinx.collections.immutable.ImmutableSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier,
    state: GameUIState,
    onGuessChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onHintClicked: () -> Unit,
) {

    Scaffold(
        modifier = modifier
            .shadow(elevation = 3.dp),
        topBar = {
            GameTopBar(modifier = modifier, currentScore = state.score, currentWord = state.wordCount)
        },
        content = { contentPadding ->
            if (state.dictionaries.isEmpty()) {
                EmptyListView(stringId = R.string.empty_saves)

            } else {
                GameLayout(
                        modifier = modifier
                            .padding(contentPadding),
                           // .verticalScroll(rememberScrollState()),
                        onGuessChanged = onGuessChanged,
                        onNextClicked = onNextClicked,
                        onSkipClicked = onSkipClicked,
                        state = state,
                        onHintClicked = onHintClicked
                    )
            }
        })
}



@Preview
@Composable
fun PreviewSavedWords() {
    En_DictionaryTheme {

    }
}