package br.com.alura.panucci.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import br.com.alura.panucci.ui.components.BottomAppBarItem
import kotlinx.coroutines.CoroutineScope

@Composable
fun PanucciNavHost(navController: NavHostController, scope: CoroutineScope, context: Context) {
    NavHost(navController = navController,
        startDestination = highlightsListRoute
    ) {
        authenticationScreen(scope, context, navController)
        highlightsListScreen(context, navController)
        menuScreen(navController)
        drinksScreen(navController)
        productDetailsScreen(navController)
        checkoutScreen(navController)
    }
}

fun NavController.navigateSingleTopWithPopUpTo(
    item: BottomAppBarItem,
) {
    val (route, navigate) = when (item) {
        BottomAppBarItem.HighlistsList -> Pair(
            drinksRoute,
            ::navigateToHighlightsList
        )

        BottomAppBarItem.Menu -> Pair(
            menuRoute,
            ::navigateToMenu
        )

        BottomAppBarItem.Drinks -> Pair(
            drinksRoute,
            ::navigateToDrinks
        )
    }
    val navOptions = navOptions {
        launchSingleTop = true
        popUpTo(route)
    }
    navigate(navOptions)
}



