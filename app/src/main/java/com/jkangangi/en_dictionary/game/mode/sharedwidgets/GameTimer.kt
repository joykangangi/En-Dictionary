package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.game.util.GameConstants.THREE_QUARTER_WORD_TIME
import com.jkangangi.en_dictionary.game.util.GameConstants.TOTAL_WORD_TIME
import com.jkangangi.en_dictionary.game.util.formatTimeInMinAndSeconds


@Composable
fun GameTimer(
    timeLeft: Int,
    modifier: Modifier = Modifier
) {

    val progress by rememberUpdatedState(newValue = timeLeft.toFloat() / TOTAL_WORD_TIME)

    val strokeColor = if (timeLeft > THREE_QUARTER_WORD_TIME) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    val remainingTime by rememberUpdatedState(newValue = formatTimeInMinAndSeconds(timeLeft))

    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            Text(
                text = remainingTime,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(70.dp),
                color = strokeColor,
                strokeWidth = 6.dp
            )
        }
    )

}


@Preview(showBackground = true)
@Composable
private fun GameTimerPreview() {
    En_DictionaryTheme {
        GameTimer(
            timeLeft = 56,
        )

    }
}