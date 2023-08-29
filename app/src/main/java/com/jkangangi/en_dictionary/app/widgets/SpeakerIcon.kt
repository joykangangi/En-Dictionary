package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SpeakerIcon(modifier: Modifier, onSpeakerClick:() -> Unit, isSpeakerOn: Boolean) {

    val speakerIcon = if (isSpeakerOn) Icons.Default.VolumeUp else Icons.Default.VolumeOff
    IconButton(
        modifier = modifier,
        onClick = onSpeakerClick,
        enabled = isSpeakerOn
    ) {
        Icon(
            imageVector = speakerIcon,
            contentDescription = null
        )
    }

}