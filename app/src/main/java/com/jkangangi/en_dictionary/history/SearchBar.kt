package com.jkangangi.en_dictionary.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.widgets.TextInput

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    TextInput(
        modifier = modifier,
        input = query,
        onInputChange = onQueryChanged,
        txtLabel = stringResource(id = R.string.search_your_history),
        txtPlaceholder = stringResource(id = R.string.search_your_history),
        onClearInput = { onQueryChanged("") },
        showWarningDescription = false
    )
}