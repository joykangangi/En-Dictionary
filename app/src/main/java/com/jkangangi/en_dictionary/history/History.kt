package com.jkangangi.en_dictionary.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

//todo room
data class HistoryItem(val word: String, val timestamp: LocalDate)


@Composable
fun HistoryScreen(historyItems: List<HistoryItem>, onItemClick: (HistoryItem) -> Unit, onClearHistory: () -> Unit) {
    Column {
        // Header with screen title and clear history button
        Text("Search History", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = onClearHistory) {
            Text("Clear History")
        }

        // List of search history items
        LazyColumn {
            items(historyItems) { item ->
                HistoryItemCard(item = item, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun HistoryItemCard(item: HistoryItem, onItemClick: (HistoryItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = item.word, style = MaterialTheme.typography.displaySmall)
            Text(text = item.timestamp.toString(), style = MaterialTheme.typography.titleMedium)
            // Additional details or actions related to the search history item
        }
    }
}
