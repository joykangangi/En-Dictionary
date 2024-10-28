package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.util.HtmlParser

@Composable
fun HintSection(
    hint: String,
    onHintClicked: () -> Unit,
    showHint: Boolean,
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
) {
    val styledMeaning = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
            append(stringResource(id = R.string.meaning))
        }
        withStyle(style = SpanStyle(fontSize = 16.sp)) {
            append(HtmlParser.htmlToString(hint))
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier,
    ) {
        Row(
            modifier = rowModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            content = {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )

                TextButton(
                    onClick = onHintClicked,
                    content = {
                        Text(
                            text = stringResource(id = R.string.show_hint),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            },
        )

        AnimatedVisibility(visible = showHint) {
            Box(
                modifier = modifier
                    .padding(6.dp)
                    .border(
                        1.dp,
                        shape = RoundedCornerShape(12),
                        color = MaterialTheme.colorScheme.secondary
                    ),
                contentAlignment = Alignment.Center,
                content = {
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = styledMeaning,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }
}