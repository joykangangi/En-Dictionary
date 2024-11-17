package com.jkangangi.en_dictionary.search.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.padding20
import com.jkangangi.en_dictionary.app.widgets.CustomFilledButton
import com.jkangangi.en_dictionary.app.widgets.TextInput
import com.jkangangi.en_dictionary.search.SearchInputErrorState
import com.jkangangi.en_dictionary.search.SearchInputEvents
import com.jkangangi.en_dictionary.search.SearchInputState
import com.jkangangi.en_dictionary.search.SearchResultUiState
import com.jkangangi.en_dictionary.search.screens.SearchResult
import com.jkangangi.en_dictionary.search.screens.components.widgets.SearchInstructionsText


// Advanced Search View

@Composable
fun ContextualSearchView(
    inputState: SearchInputState,
    errorState: SearchInputErrorState,
    searchResultUiState: SearchResultUiState,
    updateQuery: (SearchInputEvents) -> Unit,
    onSearchClick: () -> Unit,
    toWordClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            SearchInstructionsText(detailsTextId = R.string.adv_search_detail)
            Text(
                text = stringResource(id = R.string.adv_placeholder),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))


            //BeforeTarget
            TextInput(
                input = inputState.beforeSelection.value,
                onInputChange = {
                    updateQuery(SearchInputEvents.UpdateBeforeSelection(it))
                },
                txtLabel = stringResource(id = R.string.string_b4),
                txtPlaceholder = stringResource(id = R.string.b4_placeholder),
                onClearInput = { updateQuery(SearchInputEvents.UpdateBeforeSelection(beforeInput = "")) },
                isValid = errorState.beforeError
            )

            //target
            TextInput(
                input = inputState.selection.value,
                onInputChange = { updateQuery(SearchInputEvents.UpdateTarget(it)) },
                txtLabel = stringResource(id = R.string.target),
                txtPlaceholder = stringResource(id = R.string.target_placeholder),
                isRequired = true,
                onClearInput = { updateQuery(SearchInputEvents.UpdateTarget(targetInput = "")) },
                isValid = errorState.targetError
            )

            //afterTarget
            TextInput(
                modifier = modifier,
                input = inputState.afterSelection.value,
                onInputChange = { updateQuery(SearchInputEvents.UpdateAfterSelection(it)) },
                txtLabel = stringResource(id = R.string.string_after),
                txtPlaceholder = stringResource(id = R.string.after_placeholder),
                onClearInput = { updateQuery(SearchInputEvents.UpdateAfterSelection(afterInput = "")) },
                isValid = errorState.afterError
            )

            Spacer(modifier = modifier.height(8.dp))

            CustomFilledButton(
                modifier = Modifier.height(IntrinsicSize.Min),
                onBtnClicked = onSearchClick,
                buttonTextId = R.string.search_btn,
                isEnabled = errorState.targetError && errorState.beforeError && errorState.afterError
            )

            Spacer(modifier = Modifier.height(padding20()))

            AnimatedVisibility(
                visible = errorState.targetError && errorState.beforeError && errorState.afterError
            ) {
                SearchResult(
                    state = searchResultUiState,
                    toWordClick = toWordClick
                )
            }
        }
    )
}
