package com.jkangangi.en_dictionary.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jkangangi.en_dictionary.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(modifier: Modifier, currentScore: Int, currentWord: Int) {
    TopAppBar(
        title = { },
        modifier = modifier,
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                content = {
                    Text(text = stringResource(id = R.string.word_count, currentWord))
                    Text(text = stringResource(id = R.string.score_count, currentScore))
                }
            )
        }
    )
}