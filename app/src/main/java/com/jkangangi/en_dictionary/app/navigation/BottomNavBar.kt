package com.jkangangi.en_dictionary.app.navigation

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.activeElement
import com.bumble.appyx.navmodel.backstack.operation.push

private val bottomNavScreens = listOf(
    Route.Search,
    Route.Play,
    Route.History
)


@Composable
fun BottomNavigator(
    modifier: Modifier = Modifier,
    backStackNavigator: BackStack<Route>,
) {
    val navItems by remember { mutableStateOf(bottomNavScreens) }
    val backStack by backStackNavigator.elements.collectAsState()

    val currentRoute = rememberSaveable(backStack.activeElement) {
        mutableStateOf(backStack.activeElement)
    }
    //val c = rememberUpdatedState(newValue = backStack.activeElement)

    val showBottomBar = remember(currentRoute) {
        when (currentRoute.value) {
            Route.History, Route.Play, Route.Search -> true
            else -> false
        }
    }

    Log.i(
        "Navigation",
        "A.E = ${backStack.activeElement?.title}," +
                " C.R = ${currentRoute.value?.title}," +
                "B.N = ${backStackNavigator.activeElement?.title}"
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


    val interactionSource = remember { MutableInteractionSource() }

    val fontSize =  MaterialTheme.typography.bodySmall.fontSize.value
    val animatedFont by animateFloatAsState(
        targetValue = if (selected) fontSize else 0f,
        label = "bottomNav")


    Column(
        modifier = modifier
            .padding(5.dp)
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Spacer(modifier = Modifier.height(if (selected) 0.dp else 20.dp))

            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if (title != null) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = TextUnit(animatedFont, TextUnitType.Sp)
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PrevBottomNavItems() {
    Row {
        BottomAppBarItem(
            title = "Search",
            icon = Icons.Default.Search,
            selected = true,
            onClick = { }
        )
        BottomAppBarItem(
            title = "Play",
            icon = Icons.Default.Games,
            selected = false,
            onClick = { }
        )
        BottomAppBarItem(
            title = "History",
            icon = Icons.Default.History,
            selected = false,
            onClick = { }
        )
    }
}