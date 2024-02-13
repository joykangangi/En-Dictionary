package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


private const val playIcon = 1
private const val loadingBar = 2
private const val pauseIcon = 3

@Composable
fun SpeakerIcon(
    onSpeakerClick: () -> Unit,
    soundLoaded: Boolean,
    modifier: Modifier = Modifier,
) {
    //loadSound()
    //pauseSound()
    //playSound

    val speakerIcon = Icons.Default.PlayCircle
    var buttonIcon by remember {
        mutableStateOf(playIcon)
    }

    OutlinedButton(
        modifier = modifier,
        onClick = {
            if (!soundLoaded) {
                buttonIcon = loadingBar
                onSpeakerClick() //load phonetics
            } else {
                if (buttonIcon == playIcon) {
                    buttonIcon = pauseIcon
                } else if (buttonIcon == pauseIcon) {
                    buttonIcon = playIcon
                }
            }
        },
        shape = CircleShape,
        contentPadding = PaddingValues(12.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        border = BorderStroke(0.dp, Color.Transparent),
        content = {
            when (buttonIcon) {
                loadingBar -> {
                    if (soundLoaded) {
                        buttonIcon = pauseIcon
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                playIcon -> {
                    SetIcon(imageVector = Icons.Default.PlayArrow)

                }
            }
        }
    )
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


@Composable
private fun SetIcon(imageVector: ImageVector) {
    Icon(
        modifier = Modifier.size(70.dp),
        imageVector = imageVector,
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = null
    )
}