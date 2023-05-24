package com.jkangangi.en_dictionary.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

private data class BottomAppItem(
    val icon: ImageVector,
    val title: String,
    val route: String,
)

private val screens = listOf(
    BottomAppItem(
        Icons.Default.Search,
        "Search",
        "" //Todo
    ),
    BottomAppItem(
        Icons.Default.History,
        "History",
        "" //Todo
    ),
    BottomAppItem(
        Icons.Default.Bookmark,
        "Saved",
        "" //Todo
    )


)