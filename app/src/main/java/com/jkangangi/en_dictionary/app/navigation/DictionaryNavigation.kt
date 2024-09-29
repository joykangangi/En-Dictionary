package com.jkangangi.en_dictionary.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController

@Composable
fun DictionaryNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

//    NavHost(
//        navController = navController,
//        startDestination =
//    )
}


sealed class DictionaryRoutes(
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val titleId: Int? = null,
) {



}