package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity
import com.jkangangi.en_dictionary.app.util.HtmlParser
import com.jkangangi.en_dictionary.app.util.isWord
import com.jkangangi.en_dictionary.app.util.phonetics
import com.jkangangi.en_dictionary.app.widgets.SpeakerIcon


/**
 * Phonetics for one word and words;
 * Phrases don't have pronunciations;ONLY sound
 */
@Composable
fun PhoneticsSection(
    modifier: Modifier,
    dictionary: DictionaryEntity?,
    onSpeakerClick: () -> Unit
) {
    val entries = dictionary?.pronunciations?.getOrNull(0)?.entries
    val hasAudio = entries?.any { entry -> entry.audioFiles.isNotEmpty() }
    val isPhrase = entries?.any { entry -> !entry.entry.isWord() }

    val isSpeakerOn = if (isPhrase == true) hasAudio else hasAudio

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier.padding(8.dp),
        content = {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                content = {

                    SpeakerIcon(
                        modifier = modifier.size(35.dp),
                        onSpeakerClick = onSpeakerClick,
                        isSpeakerOn = isSpeakerOn == true
                    )

                    Text(
                        text = dictionary?.sentence ?: "",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )

            if (isPhrase == false) { //a word
                Text(
                    text = HtmlParser.htmlToString(entries[0].textual[0].pronunciation.phonetics()),
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    )
}