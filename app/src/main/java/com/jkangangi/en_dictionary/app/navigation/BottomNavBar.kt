package com.jkangangi.en_dictionary.app.navigation

import android.util.Log
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.composable.visibleChildrenAsState
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.active
import com.bumble.appyx.navmodel.backstack.activeElement
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import com.jkangangi.en_dictionary.app.data.model.Dictionary

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

    val currentRoute = rememberSaveable {
        mutableStateOf(backStackNavigator.activeElement)
    }

     val showBottomBar2 = remember {
         mutableStateOf(bottomNavScreens.contains(currentRoute.value))
     }
    Log.d("Bottom navigation", "Show nav ${showBottomBar2.value}")

    val showBottomBar by remember(backStackNavigator.activeElement) {
        derivedStateOf {
            when (backStackNavigator.activeElement) {
                Route.History, Route.Saved, Route.Search -> true
                else -> false
            }
        }
    }

    Log.d(
        "Btm NAVIGATION",
        "Route1 = ${backStackNavigator.activeElement?.title}, Route2 = ${currentRoute.value?.title} , showbar = $showBottomBar"
    )
    Log.d("Btm NAVIGATION", "Backstack = $backStackNavigator")
    Log.d(
        "Btm NAVIGATION",
        "Is Search Detail = ${
            currentRoute.value?.title == Route.SearchDetail(dictionary = Dictionary()).title
        }"
    )

    val selected: (screen: Route) -> Boolean = remember {
        { screen ->
            Log.d("Btm NAVIGATION", "Screen = $screen")
            screen == currentRoute.value
        }
    }


    val onItemClick = remember {
        { screen: Route ->
            backStackNavigator.push(screen)
            currentRoute.value = screen
        }
    }

    val b2 by backStackNavigator.elements.collectAsState()
    Log.d("Bottom Nav","As State activeElement${b2.activeElement}")



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
private fun BottomAppBarItem(
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
                    tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
                )
            }
            if (title != null) {
                Text(
                    text = title,
                    color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
                )
            }
        }
    )
}

