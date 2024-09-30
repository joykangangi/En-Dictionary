package com.jkangangi.en_dictionary.definitions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jkangangi.en_dictionary.app.util.DictionaryViewModelFactory
import kotlinx.serialization.Serializable


@Serializable
data class WordDetailRoute(val sentence: String)

fun NavController.navigateToWordDetail(
    sentence: String,
    navOptions: NavOptions? = null,
    ) = navigate(route = WordDetailRoute(sentence), navOptions)

fun NavGraphBuilder.wordDetailScreen(
    onBack: () -> Unit,
) {
    composable<WordDetailRoute> {
        val args = it.toRoute<WordDetailRoute>()
        WordDetailScreen(
            onBack = onBack,
            sentence = args.sentence
        )
    }
}

@Composable
internal fun WordDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DefinitionsViewModel = viewModel(factory = DictionaryViewModelFactory),
    onBack: () -> Unit,
    sentence: String,
){

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