package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
    innerColor: Color,
    outerColor: Color,
    text: String,
    shape: Shape,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    outerBoxSize: Dp = MaterialTheme.dimens.xLObjects,
    innerBoxSize: Dp = MaterialTheme.dimens.largeObject,
    fontSize: TextUnit = MaterialTheme.dimens.mediumGameText,
    shadowDp: Dp = 1.dp
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }


    Box(
        modifier = modifier
            .size(outerBoxSize)
            .background(outerColor, shape)
            .noRippleClickable(interactionSource) { onButtonClick() },
        contentAlignment = Alignment.Center,
        content = {

            Box(
                modifier = Modifier
                    .size(innerBoxSize)
                    .background(innerColor, shape)
                    .shadow(shadowDp, shape),
                contentAlignment = Alignment.Center,
                content = {

                    Text(
                        text = text,
                        fontSize = fontSize
                    )
                }
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun PreviewGameRoundButton() = En_DictionaryTheme {

    Box(modifier = Modifier.padding(mediumPadding())) {
        GameRoundButton(
            modifier = Modifier.size(300.dp),
            onButtonClick = {},
            text = "Easy",
            outerColor = MaterialTheme.colorScheme.primary,
            innerColor = MaterialTheme.colorScheme.secondary,
            shape = CircleShape,
        )
    }

}

