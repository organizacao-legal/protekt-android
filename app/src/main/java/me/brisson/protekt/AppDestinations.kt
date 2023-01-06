package me.brisson.protekt

import androidx.navigation.NavHostController
import me.brisson.protekt.AppScreens.HOME_SCREEN
import me.brisson.protekt.AppScreens.ITEM_DETAIL_SCREEN

private object AppScreens {
    const val HOME_SCREEN = "home_screen"
    const val ITEM_DETAIL_SCREEN = "item_detail_screen"
}

object AppDestinationsArgs {
    const val ITEM_ID_ARGS = "item_id"
}

object AppDestinations {
    const val HOME_ROUTE = HOME_SCREEN
}

class AppNavigationActions(private val navController: NavHostController) {
    fun navigateToItemDetail(itemId: String) {
        navController.navigate(route = "$ITEM_DETAIL_SCREEN/$itemId") {
            launchSingleTop = true
            restoreState = true
        }
    }
}

