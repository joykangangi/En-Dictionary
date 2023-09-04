package com.jkangangi.en_dictionary.app.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class HtmlParser {
    companion object {
        fun htmlToString(html: String?): AnnotatedString {
            val parsedHtml = html?.let { Jsoup.parse(it) }
            val builder = AnnotatedString.Builder()
            parsedHtml?.let { parseHtmlElements(it.body(), builder) }
            return builder.toAnnotatedString()
        }

        private fun parseHtmlElements(element: Element, builder: AnnotatedString.Builder) {
            for (child in element.childNodes()) {
                when (child.nodeName()) {
                    "#text" -> builder.append(child.toString())
                    "i" -> {
                        builder.pushStyle(style = SpanStyle(fontStyle = FontStyle.Italic))
                        parseHtmlElements(child as Element, builder)
                        builder.pop()
                    }

                    "b" -> {
                        builder.pushStyle(style = SpanStyle(fontWeight = FontWeight.Bold))
                        parseHtmlElements(child as Element, builder)
                        builder.pop()
                    }

                    else -> parseHtmlElements(child as Element, builder)
                }
            }
        }
    }
}



@Preview
@Composable
fun PrevHtml() {
    @Composable
    fun HtmlText() {
        val html = "<b>Staff only!</b>  can access your <i>entire</i> wallet."
        val formattedText = HtmlParser.htmlToString(html)
        Text(text = formattedText)
    }

    HtmlText()
}