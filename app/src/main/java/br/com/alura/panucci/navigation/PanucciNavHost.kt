package br.com.alura.panucci.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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

val bottomAppBarItems = listOf(
    BottomAppBarItem.HighlistsList,
    BottomAppBarItem.Menu,
    BottomAppBarItem.Drinks
)



