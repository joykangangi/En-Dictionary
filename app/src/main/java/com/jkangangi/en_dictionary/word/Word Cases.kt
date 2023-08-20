package com.jkangangi.en_dictionary.word

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.model.Dictionary

@Composable
fun OneWord(modifier: Modifier, word: Dictionary, onSpeakerClick: ()-> Unit, onSave: ()-> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier.padding(8.dp),
        content = {
            Text(
                text = word.pronunciations[0].entries[0].textual[0].pronunciation,
                fontFamily = FontFamily.SansSerif,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Full Phrase: ${word.sentence}",
                style = MaterialTheme.typography.titleMedium
            )
            // Spacer(modifier = modifier.height(10.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                content = {
                    IconButton(
                        onClick = onSpeakerClick,
                        enabled = word.pronunciations.all { it -> it.entries.all { it.audioFiles.isEmpty() } }) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = onSave) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null
                        )
                    }
                },
            )
        },
    )
}






@Composable
fun Phrase(modifier: Modifier, word: Dictionary, onSpeakerClick: ()-> Unit, onSave: ()-> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier.padding(8.dp),
        content = {
            /**
             * Phrases don't have phonetics
             */
            Text(
                text = "Full Phrase: ${word.sentence}",
                style = MaterialTheme.typography.titleMedium
            )
            // Spacer(modifier = modifier.height(10.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                content = {
                    IconButton(
                        onClick = onSpeakerClick,
                        enabled = word.pronunciations.all { it -> it.entries.all { it.audioFiles.isEmpty() } }) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = onSave) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null
                        )
                    }
                },
            )
        },
    )
}