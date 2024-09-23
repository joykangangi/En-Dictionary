package com.jkangangi.en_dictionary.game.easymode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.game.GameTopBar


data class EasyGameScreenState(
    val currentScore: Int,
    val currentWord: Int,
)




@Composable
fun EasyGameScreen(
    state: EasyGameScreenState,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = { GameTopBar(currentScore = state.currentScore, currentWord = state.currentWord) },
        content = {
            Column(
                modifier = modifier
                    .padding(it)
                    .padding(12.dp),
                content = {



                }
            )
        }
    )

}