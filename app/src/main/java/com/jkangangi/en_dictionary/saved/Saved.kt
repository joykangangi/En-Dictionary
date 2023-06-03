package com.jkangangi.en_dictionary.saved

import androidx.compose.runtime.Composable

@Composable
fun Saved() {
    @Composable
    fun SavedWordsScreen(savedWords: List<Word>, onDeleteClicked: (Word) -> Unit) {
        LazyColumn {
            items(savedWords) { word ->
                SavedWordCard(word = word, onDeleteClicked = onDeleteClicked)
            }
        }
    }

    @Composable
    fun SavedWordCard(word: Word, onDeleteClicked: (Word) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = word.word,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = word.phonetics,
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = word.meaning,
                    style = TextStyle(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { onDeleteClicked(word) }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}