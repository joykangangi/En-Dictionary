package com.jkangangi.en_dictionary.app.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navmodel.backstack.operation.push

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
fun BottomNavBar(navigation: Navigation, modifier: Modifier = Modifier) {

    val currentRoute by remember { mutableStateOf(navigation.currentRoute) }

    val showBottomBar by remember(currentRoute) {
        derivedStateOf {
            when (currentRoute) {
                Navigation.Route.History, Navigation.Route.Saved, Navigation.Route.Search -> true
                else -> false
            }
        }
    }

    // Handle item selection and update the current route
    val onItemSelected: (Navigation.Route) -> Unit = { route ->
        navigation.backStack.push(route)
    }

    if (showBottomBar) {
        BottomAppBar(
            modifier = modifier,
            content = {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = {
                        screens.forEach { screen ->
                            BottomAppBarItem(
                                title = screen.title,
                                icon = screen.icon,
                                selected = screen.route == currentRoute ,
                                onClick = { onItemSelected(screen.route) })
                        }
                    }
                )
            }
        )
    }
}

@Composable
private fun BottomAppBarItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .padding(5.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        content = {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = title,
                color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        }
    )
}

