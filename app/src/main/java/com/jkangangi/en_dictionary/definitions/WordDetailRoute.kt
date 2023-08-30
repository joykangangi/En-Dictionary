package com.jkangangi.en_dictionary.definitions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.jkangangi.en_dictionary.app.data.model.Dictionary


@Composable
fun WordDetailView(
    modifier: Modifier,
    viewModel: DefinitionsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    defn: Dictionary,
) {

    val onBackClick = remember { onBack }
    val context = LocalContext.current

    val onSpeakerClicked = remember {
        { viewModel.onSpeakerClick(context, word = defn) }
    }

    WordScreen(
        word = defn,
        modifier = modifier,
        onSpeakerClick = onSpeakerClicked,
        onBack = onBackClick
    )
}


