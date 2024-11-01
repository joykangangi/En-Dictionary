package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.theme.dimens
import com.jkangangi.en_dictionary.app.theme.mediumPadding
import com.jkangangi.en_dictionary.app.widgets.noRippleClickable

@Composable
fun GameRoundButton(
    text: String,
    shape: Shape,
    borderStrokeWidth: Dp,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    outerBoxSize: Dp = MaterialTheme.dimens.xLObjects,
    fontSize: TextUnit = MaterialTheme.dimens.mediumGameText,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }


    Box(
        modifier = modifier
            .size(outerBoxSize)
            .clip(shape)
            .border(borderStrokeWidth, color = MaterialTheme.colorScheme.primary, shape)
            .noRippleClickable(interactionSource) { onButtonClick() },
        contentAlignment = Alignment.Center,
        content = {

            Text(
                color = MaterialTheme.colorScheme.primary,
                text = text,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun PreviewGameRoundButton() = En_DictionaryTheme {

    Box(modifier = Modifier.padding(mediumPadding())) {
        GameRoundButton(
            onButtonClick = {},
            text = "Easy",
            shape = CircleShape,
            borderStrokeWidth = 2.dp
        )
    }

}

