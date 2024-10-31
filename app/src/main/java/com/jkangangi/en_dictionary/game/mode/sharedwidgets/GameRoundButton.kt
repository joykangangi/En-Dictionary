package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.app.theme.mediumPadding

@Composable
fun GameRoundButton(
    innerColor: Color,
    outerColor: Color,
    text: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape,
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .background(outerColor, shape)
            .clickable { onButtonClick() },
        contentAlignment = Alignment.Center,
        content = {

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .background(innerColor, shape)
                    .shadow(1.dp,shape),
                contentAlignment = Alignment.Center,
                content = {

                    Text(
                        text = text,
                        fontSize = 30.sp
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
            innerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
        )
    }

}

