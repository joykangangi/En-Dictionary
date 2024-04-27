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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.activeElement
import com.bumble.appyx.navmodel.backstack.operation.push
import com.jkangangi.en_dictionary.R

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
                                titleId = screen.titleId,
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
    titleId: Int?,
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
                    contentDescription = titleId?.let { stringResource(id = it) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if (titleId != null) {
                Text(
                    text = stringResource(id = titleId),
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
            titleId = R.string.search_btn,
            icon = Icons.Default.Search,
            selected = true,
            onClick = { }
        )
        BottomAppBarItem(
            titleId = R.string.play,
            icon = Icons.Default.Games,
            selected = false,
            onClick = { }
        )
        BottomAppBarItem(
            titleId = R.string.history,
            icon = Icons.Default.History,
            selected = false,
            onClick = { }
        )
    }
}