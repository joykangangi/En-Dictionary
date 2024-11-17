package com.jkangangi.en_dictionary.search.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.jkangangi.en_dictionary.search.SearchResultUiState
import com.jkangangi.en_dictionary.search.SearchScreenEvent
import com.jkangangi.en_dictionary.search.screens.SearchResult
import com.jkangangi.en_dictionary.search.screens.components.widgets.SearchInstructionsText


//Simple Search View
@Composable
fun BasicSearchView(
    selection: String,
    isSelectionValid: Boolean,
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
            SearchInstructionsText(detailsTextId = R.string.single_search_detail)

            //target
            TextInput(
                input = selection,
                onInputChange = {
                    SearchScreenEvent.UpdateQueries(
                        SearchInputEvents.UpdateTarget(it)
                    )
                    updateQuery(SearchInputEvents.UpdateTarget(it))
                },
                txtLabel = stringResource(id = R.string.target),
                txtPlaceholder = stringResource(id = R.string.single_placeholder),
                isRequired = true,
                onClearInput = { updateQuery(SearchInputEvents.UpdateTarget(targetInput = "")) },
                isValid = isSelectionValid
            )


            Spacer(modifier = Modifier.height(8.dp))

            CustomFilledButton(
                modifier = Modifier.height(IntrinsicSize.Min),
                onBtnClicked = onSearchClick,
                buttonTextId = R.string.search_btn,
                isEnabled = isSelectionValid
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