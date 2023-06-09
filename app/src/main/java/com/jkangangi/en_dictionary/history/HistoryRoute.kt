package com.jkangangi.en_dictionary.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.jkangangi.en_dictionary.history.HistoryScreen

class HistoryRoute(
    buildContext: BuildContext,
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        HistoryScreen(historyItems = listOf(), onClearHistory = { /*TODO*/ }, deleteWord = { /*TODO*/ })
    }
}