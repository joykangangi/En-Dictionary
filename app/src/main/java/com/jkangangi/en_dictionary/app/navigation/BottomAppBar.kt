package com.jkangangi.en_dictionary.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import com.bumble.appyx.Appyx

private data class BottomAppItem(
    val icon: ImageVector,
    val title: String,
    val route: Navigation.Route,
)

private val screens = listOf(
    BottomAppItem(
        icon = Icons.Default.Search,
        title = "Search",
        route = Navigation.Route.Search
    ),
    BottomAppItem(
        icon = Icons.Default.History,
        title = "History",
        route = Navigation.Route.History
    ),
    BottomAppItem(
        icon = Icons.Default.Bookmark,
        title = "Saved",
        route = Navigation.Route.Saved
    )
)

@Composable
fun BottomAppBar(navigation: Navigation) {

    val currentRoute by remember { mutableStateOf(navigation.currentRoute) }

    val showBottomBar by remember(currentRoute) {
        derivedStateOf {
            when (currentRoute) {
                Navigation.Route.History, Navigation.Route.Saved, Navigation.Route.Search -> true
                else -> false
            }
        }
    }

    
}

