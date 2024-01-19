package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpeakerIcon(modifier: Modifier = Modifier, onSpeakerClick:() -> Unit) {

    val speakerIcon =  Icons.Default.PlayCircle
    IconButton(
        modifier = modifier,
        onClick = onSpeakerClick
    ) {
        Icon(
            modifier = Modifier.size(70.dp),
            imageVector = speakerIcon,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
    }

}