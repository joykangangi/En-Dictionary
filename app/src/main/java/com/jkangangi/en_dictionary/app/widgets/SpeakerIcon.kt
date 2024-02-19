package com.jkangangi.en_dictionary.app.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.definitions.SoundState3


@Composable
fun SpeakerIcon(
    onSpeakerClick: () -> Unit,
    soundState2: SoundState3,
    modifier: Modifier = Modifier,
) {
    //loadSound()
    //pauseSound()
    //playSound


    OutlinedButton(
        modifier = modifier,
        onClick = {
                onSpeakerClick() //load phonetics
        },
        shape = CircleShape,
        contentPadding = PaddingValues(12.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        border = BorderStroke(0.dp, Color.Transparent),
        content = {
            soundState2.state2.icon.invoke()
        }
    )
//    IconButton(
//        modifier = modifier,
//        onClick = onSpeakerClick
//    ) {
//        Icon(
//            modifier = Modifier.size(70.dp),
//            imageVector = speakerIcon,
//            tint = MaterialTheme.colorScheme.primary,
//            contentDescription = null
//        )
//    }

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