package com.jkangangi.en_dictionary.game

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jkangangi.en_dictionary.app.util.scramble

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCard(
    modifier: Modifier,
    scrambledWord: String,
    answer: String,
    onAnswerChanged: (String) -> Unit,
) {

    ElevatedCard(
        modifier = modifier,
    ) {
        Column {
            Text(text = scrambledWord.scramble(),)
            TextField(value = answer, onValueChange = onAnswerChanged )

        }

    }

}