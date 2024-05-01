package com.jkangangi.en_dictionary.app.navigation

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.activeElement
import com.bumble.appyx.navmodel.backstack.operation.push
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.app.theme.En_DictionaryTheme

private val bottomNavScreens = listOf(
    Route.Search,
    Route.Play,
    Route.History
)


@Composable
fun BottomNavigator(
    backStackNavigator: BackStack<Route>,
    modifier: Modifier = Modifier,
) {
    val navItems by remember { mutableStateOf(bottomNavScreens) }
    val backStack by backStackNavigator.elements.collectAsState()

    val currentRoute = rememberSaveable(backStack.activeElement) {
        mutableStateOf(backStack.activeElement)
    }
    //val c = rememberUpdatedState(newValue = backStack.activeElement)

    val showBottomBar = bottomNavScreens.contains(currentRoute.value)

    Log.i(
        "Navigation",
        "A.E = ${backStack.activeElement?.titleId}," +
                " C.R = ${currentRoute.value?.titleId}," +
                "B.N = ${backStackNavigator.activeElement?.titleId}"
    )

    val selected: (screen: Route) -> Boolean = remember {
        { screen ->
            screen == currentRoute.value
        }
    }

    val onItemClick = remember {
        { screen: Route ->
            backStackNavigator.push(screen)
            currentRoute.value = screen
        }
    }

    val navBarItemColors = NavigationBarItemColors(
        selectedIconColor = MaterialTheme.colorScheme.onBackground,
        selectedTextColor = MaterialTheme.colorScheme.onBackground,
        selectedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
        disabledIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
        disabledTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    )

    if (showBottomBar) {
        BottomAppBar(
            modifier = modifier,
            content = {
                navItems.forEach { screen ->
                    NavigationBarItem(
                        colors = navBarItemColors,
                        selected = selected(screen),
                        onClick = { onItemClick(screen) },
                        label = {
                            if (screen.titleId != null) {
                                Text(text = stringResource(id = screen.titleId))
                            }
                        },
                        icon = {
                            if (screen.selectedIcon != null && screen.unselectedIcon != null) {
                                val icon =
                                    if (selected(screen)) screen.selectedIcon else screen.unselectedIcon
                                val contentDescription =
                                    screen.titleId?.let { stringResource(id = it) }

                                Icon(
                                    imageVector = icon,
                                    contentDescription = contentDescription
                                )
                            }
                        }
                    )
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PrevBottomNavItems() {
    En_DictionaryTheme {

        Row {
            NavigationBarItem(
                selected = true,
                onClick = { },
                label = {
                    Text(text = stringResource(id = R.string.search_btn))

                },
                icon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            )
            NavigationBarItem(
                selected = false,
                onClick = { },
                label = {
                    Text(text = stringResource(id = R.string.play))
                },
                icon = {
                    Icon(imageVector = Icons.Outlined.Games, contentDescription = null)
                }
            )
            NavigationBarItem(
                selected = false,
                onClick = { },
                label = {
                    Text(text = stringResource(id = R.string.history))
                },
                icon = {
                    Icon(imageVector = Icons.Outlined.History, contentDescription = null)
                }
            )
        }
    }
}