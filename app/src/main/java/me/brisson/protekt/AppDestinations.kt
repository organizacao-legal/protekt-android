package me.brisson.protekt

import androidx.navigation.NavHostController
import me.brisson.protekt.AppDestinationsArgs.ITEM_ID_ARGS
import me.brisson.protekt.AppScreens.CREATE_CREDENTIAL_SCREEN
import me.brisson.protekt.AppScreens.HOME_SCREEN
import me.brisson.protekt.AppScreens.ITEM_DETAIL_SCREEN

private object AppScreens {
    const val HOME_SCREEN = "home_screen"
    const val ITEM_DETAIL_SCREEN = "item_detail_screen"
    const val CREATE_CREDENTIAL_SCREEN = "create_credential_screen"
}

object AppDestinationsArgs {
    const val ITEM_ID_ARGS = "item_id"
}

object AppDestinations {
    const val HOME_ROUTE = HOME_SCREEN
    const val ITEM_DETAIL_ROUTE = "$ITEM_DETAIL_SCREEN/{$ITEM_ID_ARGS}"
    const val CREATE_CREDENTIAL_ROUTE = "$CREATE_CREDENTIAL_SCREEN?$ITEM_ID_ARGS={$ITEM_ID_ARGS}"
}

class AppNavigationActions(private val navController: NavHostController) {
    fun navigateToItemDetail(itemId: String) {
        navController.navigate(route = "$ITEM_DETAIL_SCREEN/$itemId") {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToCreateCredential(itemId: String? = null) {
        navController.navigate(route = "$CREATE_CREDENTIAL_SCREEN?$ITEM_ID_ARGS=$itemId") {
            launchSingleTop = true
            restoreState = true
        }
    }
}

