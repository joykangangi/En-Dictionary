package com.jkangangi.en_dictionary.definitions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory


@Composable
fun DefinitionView(
    modifier: Modifier,
    viewModel: DefinitionsViewModel = viewModel(factory = DictionaryViewModelFactory),
    onBack: () -> Unit,
    sentence: String,
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getDictionary(sentence)
    })

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            viewModel.clearSoundResources()
        }
    })

    val dictionary = viewModel.dictionary.collectAsState()


    val context = LocalContext.current.applicationContext
    val onSpeakerClicked = {
        viewModel.onSpeakerClick(context, dictionary = dictionary.value)
    }


    DefinitionScreen(
        dictionary = dictionary.value,
        modifier = modifier,
        onSpeakerClick = onSpeakerClicked,
        onBack = onBack,
    )
}


