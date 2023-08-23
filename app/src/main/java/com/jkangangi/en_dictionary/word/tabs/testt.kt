package com.jkangangi.en_dictionary.word.tabs

import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FormattedHtml(html: String) {
    val annotatedString = buildAnnotatedString {
        appendInlineContent(html)
    }

    // Render the AnnotatedString with the Html content
    Text(text = annotatedString)
}

@Preview
@Composable
fun PreviewFormattedHtml() {
    FormattedHtml("<i>This is italic text</i>")
}