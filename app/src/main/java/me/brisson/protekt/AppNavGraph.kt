package me.brisson.protekt

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.brisson.protekt.AppDestinations.HOME_ROUTE
import me.brisson.protekt.AppDestinations.ITEM_DETAIL_ROUTE
import me.brisson.protekt.presentation.home.HomeScreen
import me.brisson.protekt.presentation.item_detail.ItemDetailScreen

@ExperimentalMaterial3Api
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
        composable(route = HOME_ROUTE) {
            HomeScreen(
                onItem = { navActions.navigateToItemDetail(it) })
        }

        composable(route = ITEM_DETAIL_ROUTE) {
            ItemDetailScreen(
                onBack = { navController.popBackStack() },
                onEdit = { }
            )
        }
    }
}