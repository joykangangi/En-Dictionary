package com.jkangangi.en_dictionary.definitions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefinitionTopBar(onBack:() -> Unit, modifier: Modifier = Modifier) {

    TopAppBar(
        title = {
            Text(text = "")
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back)
                )
            }
        },
        modifier = modifier,
    )

}

@Preview(showBackground = true)
@Composable
fun PreviewDefTopBar() {
    En_DictionaryTheme {
        DefinitionTopBar(onBack = {  })
    }
}