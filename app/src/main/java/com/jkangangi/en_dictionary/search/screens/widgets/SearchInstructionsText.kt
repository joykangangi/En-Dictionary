package com.jkangangi.en_dictionary.search.screens.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.dimens

//Shown at the top of [SearchScreen]
@Composable
fun SearchInstructionsText(
    detailsTextId: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Icon(
                imageVector = Icons.Default.LocalLibrary,
                contentDescription = stringResource(id = R.string.search_btn),
                modifier = Modifier
                    .size(MaterialTheme.dimens.mediumObject),
            )

            Text(
                text = stringResource(id = detailsTextId),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    )
}