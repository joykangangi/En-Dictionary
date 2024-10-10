package com.jkangangi.en_dictionary.game.mode.sharedwidgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.game.util.GameConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    currentScore: Int,
    currentWord: Int,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { },
        actions = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(text = stringResource(id = R.string.word_count, currentWord,
                        GameConstants.MAX_WORDS
                    ), modifier = Modifier.padding(6.dp))
                    Text(text = stringResource(id = R.string.score_count, currentScore,
                        GameConstants.MAX_SCORE
                    ), modifier = Modifier.padding(6.dp))
                }
            )
        }
    )
}