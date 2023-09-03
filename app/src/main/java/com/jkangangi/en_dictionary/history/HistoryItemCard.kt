package com.jkangangi.en_dictionary.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.data.local.DictionaryEntity

@Composable
fun HistoryItemCard(
    dictionary: DictionaryEntity,
    onDeleteWord: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    /*val onDeleteDictionary = remember {
        { onDeleteWord(dictionary) }
    }*/
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = dictionary.sentence,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.weight(1f)
            )
            IconButton(onClick = {onDeleteWord(listOf(dictionary.sentence))}, content = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete),
                )
            })
        }
    }
}

@Preview
@Composable
fun PrevHistoryItem() {
    val historyItem = DictionaryEntity(sentence = "What other majesty")
    HistoryItemCard(dictionary = historyItem, onDeleteWord = { })
}