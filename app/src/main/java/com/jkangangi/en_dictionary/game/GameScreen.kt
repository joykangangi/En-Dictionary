package com.jkangangi.en_dictionary.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.widgets.EmptyListView
import com.jkangangi.en_dictionary.history.EmptyHistory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier,
    currentScore: Int,
    wordCount: Int,
    dictionaries: ImmutableSet<DictionaryEntity>


) {
    Scaffold(
        modifier = modifier
            .shadow(elevation = 3.dp),
        topBar = {
            GameTopBar(modifier = modifier, currentScore = currentScore, currentWord = wordCount)
        },
        content = { contentPadding ->
            if (dictionaries.isEmpty()) {
                EmptyListView(stringId = R.string.empty_saves)

            } else {

            }
        })
}

@Composable
private fun ButtonSection(modifier: Modifier, onSkipClicked: () -> Unit, onNextClicked: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier,
        content = {
            OutlinedButton(onClick = onSkipClicked) {
                Text(text = "Skip")
            }

            Button(onClick = onNextClicked) {
                Text(text = "Next")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewSavedWords() {
    En_DictionaryTheme {
        Scaffold {

        }
    }
}