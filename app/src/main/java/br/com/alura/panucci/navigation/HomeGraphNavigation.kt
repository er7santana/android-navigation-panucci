package br.com.alura.panucci.navigation

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import br.com.alura.panucci.ui.components.BottomAppBarItem

internal const val homeGraphRoute = "home"

internal fun NavGraphBuilder.homeGraph(
    context: Context,
    navController: NavHostController
) {
    navigation(startDestination = highlightsListRoute, route = homeGraphRoute) {
        highlightsListScreen(context, navController)
        menuScreen(navController)
        drinksScreen(navController)
    }
}

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    navigate(homeGraphRoute, navOptions)
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