package com.jkangangi.en_dictionary.app.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.activeElement
import com.bumble.appyx.navmodel.backstack.operation.singleTop

private val bottomNavScreens = listOf(
    Route.Search,
    Route.Saved,
    Route.History
)

@Composable
fun BottomNavigator(
    modifier: Modifier = Modifier,
    backStackNavigator: BackStack<Route>,
) {
    val navItems by remember { mutableStateOf(bottomNavScreens) }

    val currentRoute = remember {
        mutableStateOf(backStackNavigator.activeElement)
    }
    val showBottomBar by remember(currentRoute.value) {
        derivedStateOf {
            when (currentRoute.value) {
                Route.History, Route.Saved, Route.Search -> true
                else -> false
            }
        }
    }

    val selected: (screen: Route) -> Boolean = remember {
        { screen ->
            screen == currentRoute.value
        }
    }

    val onItemClick = remember {
        { screen: Route ->
            backStackNavigator.singleTop(screen)
            currentRoute.value = screen
        }
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
                        navItems.forEach { screen ->
                            BottomAppBarItem(
                                title = screen.title,
                                icon = screen.icon,
                                selected = selected(screen),
                                onClick = { onItemClick(screen) },
                            )
                        }
                    }
                )
            }
        )
    }
}

@Composable
fun BottomAppBarItem(
    title: String?,
    icon: ImageVector?,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .padding(5.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
            if (title != null) {
                Text(
                    text = title,
                    color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }
    )
}

