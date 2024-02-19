package com.jkangangi.en_dictionary.definitions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


data class SoundState(
    val play: Boolean = true,
    val load: Boolean = false,
    val pause: Boolean = false
    )

data class SoundState3(val state2: SoundState2 = SoundState2.Playing)

enum class SoundState2(val state: Int, val icon: @Composable () -> Unit) {
    Playing(
        state = 1,
        icon = {
            SetIcon(imageVector = Icons.Default.PlayCircle)
        }
    ),

    Loading(
        state = 2,
        icon = {
            Box(
                modifier = Modifier.clip(CircleShape),
                content = {
                    CircularProgressIndicator()
                }
            )
        }
    ),

    Pause(
        state = 3,
        icon = {
           SetIcon(imageVector = Icons.Default.PauseCircle)
        }
    )
}

@Composable
private fun SetIcon(imageVector: ImageVector) {
    Icon(
        modifier = Modifier.size(40.dp),
        imageVector = imageVector,
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = null
    )
}