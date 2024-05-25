package com.jkangangi.en_dictionary.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import kotlinx.coroutines.delay

@Composable
fun GameTimer(
    totalTime: Long,
    onTimeUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var timeLeft by remember {
        mutableLongStateOf(totalTime)
    }

    val progress by remember {
        derivedStateOf { timeLeft.toFloat() / totalTime }
    }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000)
            timeLeft -= 1000
        }else{
            onTimeUp()
        }
    }

    Column(
        modifier = modifier.wrapContentSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        content = {
            Text(
                text = "${(timeLeft / 1000).toInt()} seconds",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(100.dp),
                color = MaterialTheme.colorScheme.primary,
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
            totalTime = 60000L,
            onTimeUp = { }
        )

    }
}