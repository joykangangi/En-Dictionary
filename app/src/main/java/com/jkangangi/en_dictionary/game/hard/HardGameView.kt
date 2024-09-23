package com.jkangangi.en_dictionary.game.hard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HardGameView(modifier: Modifier = Modifier) {

    Scaffold(
        content = {
            Box(modifier = modifier.padding(it).fillMaxSize())
        }
    )
}