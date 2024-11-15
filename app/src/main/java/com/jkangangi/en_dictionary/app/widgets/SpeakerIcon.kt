package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun SpeakerIcon(
    onSpeakerClick: () -> Unit,
    modifier: Modifier = Modifier,
    speakerSize: Dp = 70.dp
) {


    IconButton(
        modifier = modifier,
        onClick = onSpeakerClick,
        content = {
            Icon(
                modifier = Modifier.size(speakerSize),
                imageVector = Icons.Default.PlayCircle,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        }
    )

}