package com.jkangangi.en_dictionary.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Games
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.jkangangi.en_dictionary.R
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

enum class MainAppRoutes(
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val titleId: Int? = null,
    val route: KClass<*>,
) {

    SEARCH(
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Outlined.Search,
        titleId = R.string.search_btn,
        route = SearchRoute::class
    ),

    SEARCH_DETAIL(
        titleId = R.string.search_detail,
        route = SearchDetail::class
    ),

    PLAY(
        selectedIcon = Icons.Default.Games,
        unselectedIcon = Icons.Outlined.Games,
        titleId = R.string.play,
        route = Play::class
    ),

    HISTORY(
        selectedIcon = Icons.Default.History,
        unselectedIcon = Icons.Outlined.History,
        titleId = R.string.history,
        route = History::class
    )

}

@Serializable
data object SearchRoute

@Serializable
data class SearchDetail(val sentence: String)

@Serializable
data object Play

@Serializable
data object History