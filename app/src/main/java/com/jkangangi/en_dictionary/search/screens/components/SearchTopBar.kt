package com.jkangangi.en_dictionary.search.screens.components

import androidx.compose.animation.animateContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    isDarkTheme: Boolean,
    toggleTheme: (Boolean) -> Unit,
    onFontClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.home_text),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {

            IconButton(
                modifier = Modifier,
                onClick = onFontClick,
                content = {
                    Icon(
                        imageVector = Icons.Default.FontDownload,
                        contentDescription = null,
                    )
                }
            )

            IconButton(
                modifier = Modifier.animateContentSize(),
                onClick = { toggleTheme(!isDarkTheme) },
                content = {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.WbSunny else Icons.Default.DarkMode,
                        contentDescription = null,
                    )
                }
            )
        },
        modifier = modifier
    )

}

@Preview
@Composable
fun PreviewSearchTopBar() {
    SearchTopBar(
        isDarkTheme = false,
        toggleTheme = { },
        onFontClick = { }
    )

}