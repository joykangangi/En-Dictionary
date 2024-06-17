package com.jkangangi.en_dictionary.game

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme
import com.jkangangi.en_dictionary.game.GameConstants.THOUSAND
import com.jkangangi.en_dictionary.game.GameConstants.TOTAL_TIME


@Composable
fun GameTimer(
    timeLeft: Long,
    modifier: Modifier = Modifier
) {
//    var time by remember {
//        mutableLongStateOf(timeLeft)
//    }
    Log.i("GameTimer","$timeLeft")

    val progress by remember {
        derivedStateOf { timeLeft.toFloat() / TOTAL_TIME}
    }
//java.lang.IllegalArgumentException:
// DerivedState(value=<Not calculated>)@9504179 cannot be saved using the
// current SaveableStateRegistry.
// The default implementation only supports types which can be stored inside the Bundle.
// Please consider implementing a custom Saver for this class and pass it to rememberSaveable().
//

//    LaunchedEffect(key1 = time) {
//        if (time > 0) {
//            delay(1000)
//            time -= 1000
//        }
//    }
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            Text(
                text = "${(timeLeft / THOUSAND).toInt()} seconds",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
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
            timeLeft = 56_000L,
        )

    }
}