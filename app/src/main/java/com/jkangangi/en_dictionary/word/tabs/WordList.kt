package com.jkangangi.en_dictionary.word.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WordList(modifier: Modifier, words: List<String>) {
    Column(modifier.padding(12.dp)) {
        words.forEachIndexed { index, word ->
            Text(text = "${index+1} $word")
        }
    }
}