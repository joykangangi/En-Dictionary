package com.jkangangi.en_dictionary.app.navigation

import android.util.Log
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.jkangangi.en_dictionary.game.navigateToPlay
import com.jkangangi.en_dictionary.history.navigateToHistory
import com.jkangangi.en_dictionary.search.navigateToMainSearch

private val bottomNavScreens: List<MainAppRoutes> = listOf(
    MainAppRoutes.SEARCH,
    MainAppRoutes.PLAY,
    MainAppRoutes.HISTORY,
)

@Composable
fun AppBottomNavigator(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val navStackBackEntry by navHostController.currentBackStackEntryAsState()

    val showBottomBar by remember(key1 = navStackBackEntry?.destination?.route) {
        derivedStateOf {
            when(navStackBackEntry?.destination?.route) {
                MainAppRoutes.SEARCH.route.qualifiedName,
                MainAppRoutes.PLAY.route.qualifiedName,
                MainAppRoutes.HISTORY.route.qualifiedName -> true
                else -> false
            }
        }
    }

    Log.i(
        "Navigation",
        "show nav = $showBottomBar, ${navStackBackEntry?.destination?.route }, ${MainAppRoutes.HISTORY.route.qualifiedName}"
    )



    val onItemClick = remember {
        { screen: MainAppRoutes ->
            val mainAppNavOptions = navOptions {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true

                // Restore state when re-selecting a previously selected item
                restoreState = true
            }
            when (screen) {
                MainAppRoutes.SEARCH -> navHostController.navigateToMainSearch(mainAppNavOptions)
                MainAppRoutes.PLAY -> navHostController.navigateToPlay(mainAppNavOptions)
                MainAppRoutes.HISTORY -> navHostController.navigateToHistory(mainAppNavOptions)
            }
        }
    }

    val selected = remember {
        { screen: MainAppRoutes ->
            navStackBackEntry?.destination?.route == screen.route.qualifiedName
        }
    }


    val navItems by remember { mutableStateOf(bottomNavScreens) }

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