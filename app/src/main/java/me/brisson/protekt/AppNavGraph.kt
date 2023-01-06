package me.brisson.protekt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.brisson.protekt.AppDestinations.HOME_ROUTE
import me.brisson.protekt.presentation.home.HomeScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE,
    navActions: AppNavigationActions = remember(navController) {
        AppNavigationActions(navController)
    }
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = AppDestinations.HOME_ROUTE) {
            HomeScreen()
        }
    }
}