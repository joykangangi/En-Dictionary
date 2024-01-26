package com.jkangangi.en_dictionary.settings.fonts

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bumble.appyx.core.collections.immutableListOf
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme


val fontList = immutableListOf(Font.Sans, Font.Mono, Font.Merriweather)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontDropDownMenu(
    font: Font,
    onFontChange: (Font) -> Unit,
    modifier: Modifier = Modifier,
) {

    var expanded by remember {
        mutableStateOf(false)
    }


    //menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        content = {

            TextField(
                value = font.name,
                onValueChange = { },
                modifier = modifier.menuAnchor(),
                readOnly = true,
                label = { Text("Fonts") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    fontList.forEach { fontOption ->
                        DropdownMenuItem(
                            text = { Text(text = fontOption.name) },
                            onClick = {
                                onFontChange(fontOption)
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            )
        }
    )
}

@Preview
@Composable
fun PreviewFontDropDown() {
    En_DictionaryTheme {
        FontDropDownMenu(font = fontList[0], onFontChange = { })
    }

}