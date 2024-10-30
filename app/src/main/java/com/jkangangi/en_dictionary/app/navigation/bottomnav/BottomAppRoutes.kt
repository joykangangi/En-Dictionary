package com.jkangangi.en_dictionary.app.navigation.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.jkangangi.en_dictionary.R
import com.jkangangi.en_dictionary.game.GameRoute
import com.jkangangi.en_dictionary.history.HistoryRoute
import com.jkangangi.en_dictionary.search.MainSearchRoute
import kotlin.reflect.KClass

enum class BottomAppRoutes(
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val titleId: Int? = null,
    val route: KClass<*>,
) {

    SEARCH(
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Outlined.Search,
        titleId = R.string.search_btn,
        route = MainSearchRoute::class
    ),

    PLAY(
        selectedIcon = Icons.Default.Games,
        unselectedIcon = Icons.Outlined.Games,
        titleId = R.string.play,
        route = GameRoute.GameIntroRoute::class
    ),

    HISTORY(
        selectedIcon = Icons.Default.History,
        unselectedIcon = Icons.Outlined.History,
        titleId = R.string.history,
        route = HistoryRoute::class
    )

}