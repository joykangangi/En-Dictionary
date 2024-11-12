package com.jkangangi.en_dictionary.search.screens.components.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.widgets.CustomFilledButton
import com.jkangangi.en_dictionary.app.widgets.CustomOutlinedButton


//The 2 buttons in [SearchScreen]
@Composable
fun SearchButtonsRow(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    showAdvancedSearch: MutableState<Boolean>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            CustomOutlinedButton(
                modifier = Modifier.fillMaxHeight(),
                onBtnClicked = {
                    showAdvancedSearch.value = !showAdvancedSearch.value
                },
                buttonTextId = if (showAdvancedSearch.value) R.string.simple_search_btn else R.string.adv_search_btn
            )

            CustomFilledButton(
                modifier = Modifier
                    .fillMaxHeight(),
                onBtnClicked = onSearchClick,
                buttonTextId = R.string.search_btn,
                isEnabled = enabled
            )
        }
    )
}